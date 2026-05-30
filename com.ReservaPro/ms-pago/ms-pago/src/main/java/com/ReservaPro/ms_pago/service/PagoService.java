package com.ReservaPro.ms_pago.service;

import com.ReservaPro.ms_pago.client.PagosClient;
import com.ReservaPro.ms_pago.dto.request.PagoRequest;
import com.ReservaPro.ms_pago.dto.response.*;
import com.ReservaPro.ms_pago.exception.PagoNoEncontradoException;
import com.ReservaPro.ms_pago.exception.PagoNoReembolsadoException;
import com.ReservaPro.ms_pago.mapper.PagoMapper;
import com.ReservaPro.ms_pago.model.Estado;
import com.ReservaPro.ms_pago.model.Pago;
import com.ReservaPro.ms_pago.repository.PagoRespository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRespository pagoRespository;
    private final PagoMapper pagoMapper;
    private final PagosClient pagosClient;

    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    //CREAR_PAGO
    @Transactional
    public PagoResponse procesarPago(PagoRequest pago) {
        log.info("Iniciando proceso de pago");
        double montoPago = pago.getMontoPago();

        if (pago.getCodigoPromocion() != null && !pago.getCodigoPromocion().trim().isEmpty()) {
            CalcularDescuentoResponse respuestaPromocion = pagosClient.calcularYActivarPromocion(pago.getCodigoPromocion(), montoPago);

            montoPago = respuestaPromocion.getMontoFinal();
        }

        if (montoPago <= 0)
            throw new PagoNoEncontradoException("El monto de pago debe ser mayor a cero para validar descuento");

        Pago pagoAux = pagoMapper.toEntity(pago);
        pagoAux.setMontoPago(montoPago);
        pagoAux.setEstadoPago(Estado.PENDIENTE);
        pagoAux.setFechaPago(pago.getFechaPago() != null ? pago.getFechaPago() : LocalDate.now());

        return pagoMapper.toResponse(pagoRespository.save(pagoAux));
    }

    //ACTUALIZAR CONFIRMAR_PAGO
    @Transactional
    public PagoResponse validarPago(Long idPago) {
        Pago pago = pagoRespository.findById(idPago).orElseThrow(() -> new PagoNoEncontradoException("Pago no encontrado"));
        if (pago.getEstadoPago() == Estado.PAGADO) throw new PagoNoEncontradoException("El pago ya esta realizado");
        pago.setEstadoPago(Estado.PAGADO);

        return pagoMapper.toResponse(pagoRespository.save(pago));
    }

    //ACTUALIZAR REEMBOLSAR_PAGO
    @Transactional
    public PagoResponse reembolsarPago(Long idPago) {
        Pago pago = pagoRespository.findById(idPago).orElseThrow(() -> new PagoNoEncontradoException("Pago no encontrado"));
        if (pago.getEstadoPago() != Estado.PAGADO)
            throw new PagoNoReembolsadoException("Solo se puede reembolsar un pago ya pagado");
        pago.setEstadoPago(Estado.REEMBOLSO);

        return pagoMapper.toResponse(pagoRespository.save(pago));
    }

    //OBTENER ESTADO_PAGO
    public EstadoResponse verEstadoPago(Long idPago) {
        log.info("Se esta obteniendo el estado de pago");
        Pago pago = pagoRespository.findById(idPago).orElseThrow(() -> new PagoNoEncontradoException("Pago no encontrado"));
        EstadoResponse estadoResponse = new EstadoResponse();
        estadoResponse.setIdEstado(pago.getIdPago());
        estadoResponse.setEstadoPago(pago.getEstadoPago().name());

        return estadoResponse;
    }

    //OBTENER MONTO_PAGO
    public MontoResponse verMontoPago(Long idPago) {
        log.info("Se esta obteniendo el monto de pago");
        Pago pago = pagoRespository.findById(idPago).orElseThrow(() -> new PagoNoEncontradoException("Pago no encontrado"));
        MontoResponse montoResponse = new MontoResponse();
        montoResponse.setIdPago(pago.getIdPago());
        montoResponse.setMontoPago(pago.getMontoPago());

        return montoResponse;
    }

    //PRUEBA
    public PruebaResponse verPrueba(Long idPago) {
        log.info("Se esta realizando una prueba");
        Pago pago = pagoRespository.findById(idPago).orElseThrow(() -> new PagoNoEncontradoException("Pago no encontrado"));
        PruebaResponse pruebaResponse = new PruebaResponse();
        pruebaResponse.setIdPrueba(pago.getIdPago());
        pruebaResponse.setPrueba(pago.getPrueba());

        return pruebaResponse;
    }
}
