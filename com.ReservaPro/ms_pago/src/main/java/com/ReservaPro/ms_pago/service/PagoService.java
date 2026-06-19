package com.ReservaPro.ms_pago.service;

import com.ReservaPro.ms_pago.client.PagosClient;
import com.ReservaPro.ms_pago.dto.request.PagoRequest;
import com.ReservaPro.ms_pago.dto.response.*;
import com.ReservaPro.ms_pago.exception.PagoNoEncontradoException;
import com.ReservaPro.ms_pago.exception.PagoNoReembolsadoException;
import com.ReservaPro.ms_pago.mapper.PagoMapper;
import com.ReservaPro.ms_pago.model.Estado;
import com.ReservaPro.ms_pago.model.Pago;
import com.ReservaPro.ms_pago.repository.PagoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;
    private final PagosClient pagosClient;

    private static final Logger log = LoggerFactory.getLogger(PagoService.class);


    //LISTA DE PAGOS
    @Transactional
    public List<Pago> listarPagos(){
        log.info("Se esta mostrando el listado de los pagos");
        return pagoRepository.findAll();
    }

    //CREAR_PAGO
    @Transactional
    public PagoResponse procesarPago(PagoRequest pago) {
        log.info("Iniciando proceso de pago");
        double montoOriginal = pago.getMontoPago();
        double montoFinalPago = montoOriginal;
        boolean aplicaDescuento = false;

        String mensajeDescuento = "No aplica";

        if (pago.getCodigoPromocion() != null && !pago.getCodigoPromocion().trim().isEmpty()) {
            try {
                CalcularDescuentoResponse respuestaPromocion = pagosClient.calcularYActivarPromocion(pago.getCodigoPromocion(), montoFinalPago);

                montoFinalPago = respuestaPromocion.getMontoFinal();
                aplicaDescuento = true;

                double porcentaje = (respuestaPromocion.getDescuentoAplicado() / montoOriginal) * 100;

                //QUITA DECIMALES
                String porcentajeTexto = String.format("%.0f", porcentaje);


                mensajeDescuento = "Si aplica ("+porcentajeTexto+ "%)";
            }catch (feign.FeignException e){
                log.warn("No se pudo aplicar el código promocional '{}'. Motivo: {}", pago.getCodigoPromocion(), e.getMessage());
                aplicaDescuento = false;
            }
        }

        if (montoFinalPago <= 0)
            throw new PagoNoEncontradoException("El monto de pago debe ser mayor a cero para validar descuento");

        Pago pagoAux = pagoMapper.toEntity(pago);

        pagoAux.setMontoOriginal(montoOriginal);
        pagoAux.setMontoPago(montoFinalPago);
        pagoAux.setCodigoPromocion(pago.getCodigoPromocion());
        pagoAux.setAplicaDescuento(aplicaDescuento);

        pagoAux.setEstadoPago(Estado.PENDIENTE);
        pagoAux.setFechaPago(pago.getFechaPago() != null ? pago.getFechaPago() : LocalDate.now());

        Pago pagoGuardado = pagoRepository.save(pagoAux);
        PagoResponse responseFinal = pagoMapper.toResponse(pagoGuardado);

        responseFinal.setDescuento(mensajeDescuento);
        return responseFinal;
    }

    //ACTUALIZAR CONFIRMAR_PAGO
    @Transactional
    public PagoResponse validarPago(Long idPago) {
        log.info("Se esta validando el pago seleccionado");
        Pago pago = pagoRepository.findById(idPago).orElseThrow(() -> new PagoNoEncontradoException("Pago no encontrado"));
        if (pago.getEstadoPago() == Estado.PAGADO) throw new PagoNoEncontradoException("El pago ya esta realizado");
        pago.setEstadoPago(Estado.PAGADO);

        return pagoMapper.toResponse(pagoRepository.save(pago));
    }

    //ELIMINAR PAGO
    @Transactional
    public void eliminarPago(Long idPago){
        log.info("Se esta eliminando el pago seleccionado");
        pagoRepository.deleteById( idPago);
    }
    //ACTUALIZAR REEMBOLSAR_PAGO
    @Transactional
    public PagoResponse reembolsarPago(Long idPago) {
        Pago pago = pagoRepository.findById(idPago).orElseThrow(() -> new PagoNoEncontradoException("Pago no encontrado"));
        if (pago.getEstadoPago() != Estado.PAGADO)
            throw new PagoNoReembolsadoException("Solo se puede reembolsar un pago ya pagado");
        pago.setEstadoPago(Estado.REEMBOLSO);

        return pagoMapper.toResponse(pagoRepository.save(pago));
    }

    //OBTENER ESTADO_PAGO
    public EstadoResponse verEstadoPago(Long idPago) {
        log.info("Se esta obteniendo el estado de pago");
        Pago pago = pagoRepository.findById(idPago).orElseThrow(() -> new PagoNoEncontradoException("Pago no encontrado"));
        EstadoResponse estadoResponse = new EstadoResponse();
        estadoResponse.setIdEstado(pago.getIdPago());
        estadoResponse.setEstadoPago(pago.getEstadoPago().name());

        return estadoResponse;
    }

    //OBTENER MONTO_PAGO
    public MontoResponse verMontoPago(Long idPago) {
        log.info("Se esta obteniendo el monto de pago");
        Pago pago = pagoRepository.findById(idPago).orElseThrow(() -> new PagoNoEncontradoException("Pago no encontrado"));
        MontoResponse montoResponse = new MontoResponse();
        montoResponse.setIdPago(pago.getIdPago());
        montoResponse.setMontoPago(pago.getMontoPago());

        return montoResponse;
    }
}
