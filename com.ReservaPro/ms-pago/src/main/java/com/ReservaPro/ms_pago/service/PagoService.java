package com.ReservaPro.ms_pago.service;

import com.ReservaPro.ms_pago.client.PromocionClient;
import com.ReservaPro.ms_pago.client.ReservaClient;
import com.ReservaPro.ms_pago.dto.request.PagoRequest;
import com.ReservaPro.ms_pago.dto.request.ReservaPagoRequest;
import com.ReservaPro.ms_pago.dto.response.CalcularDescuentoResponse;
import com.ReservaPro.ms_pago.dto.response.EstadoResponse;
import com.ReservaPro.ms_pago.dto.response.MontoResponse;
import com.ReservaPro.ms_pago.dto.response.PagoResponse;
import com.ReservaPro.ms_pago.enums.Estado;
import com.ReservaPro.ms_pago.exception.PagoNoEncontradoException;
import com.ReservaPro.ms_pago.exception.PagoNoReembolsadoException;
import com.ReservaPro.ms_pago.mapper.PagoMapper;
import com.ReservaPro.ms_pago.model.Pago;
import com.ReservaPro.ms_pago.repository.PagoRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {

    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;

    // Feign Clients
    private final PromocionClient promocionClient;
    private final ReservaClient reservaClient;

    @Transactional(readOnly = true)
    public List<Pago> listarPagos() {
        log.info("Listando todos los pagos");
        return pagoRepository.findAll();
    }

    @Transactional
    public PagoResponse procesarPago(PagoRequest request) {
        log.info("Procesando pago para reserva: {}", request.getIdReserva());

        double montoOriginal = request.getMontoOriginal();
        double montoFinal = request.getMontoPago();
        boolean aplicaDescuento = false;
        String mensajeDescuento = "No aplica";

        // Aplicar promoción si existe
        if (request.getCodigoPromocion() != null && !request.getCodigoPromocion().trim().isEmpty()) {
            try {
                CalcularDescuentoResponse respuesta = promocionClient.aplicarPromocion(
                        request.getCodigoPromocion(),
                        montoFinal
                );

                if (respuesta != null) {
                    montoFinal = respuesta.getMontoFinal();
                    aplicaDescuento = true;
                    double porcentaje = ((respuesta.getDescuentoAplicado() / montoOriginal) * 100);
                    mensajeDescuento = "Si aplica (" + String.format("%.0f", porcentaje) + "%)";
                }
            } catch (FeignException e) {
                log.warn("No se pudo aplicar promoción '{}': {}", request.getCodigoPromocion(), e.getMessage());
                aplicaDescuento = false;
            }
        }

        if (montoFinal <= 0) {
            throw new IllegalArgumentException("El monto final debe ser mayor a cero");
        }

        Pago pago = pagoMapper.toEntity(request);
        pago.setIdReserva(request.getIdReserva());
        pago.setMontoOriginal(montoOriginal);
        pago.setMontoPago(montoFinal);
        pago.setCodigoPromocion(request.getCodigoPromocion());
        pago.setAplicaDescuento(aplicaDescuento);

        //El pago se crea como PENDIENTE
        pago.setEstadoPago(Estado.PENDIENTE);

        Pago pagoGuardado = pagoRepository.save(pago);
        log.info("Pago creado con ID: {} - Estado: {}", pagoGuardado.getId(), pagoGuardado.getEstadoPago().getValor());

        PagoResponse response = pagoMapper.toResponse(pagoGuardado);
        response.setDescuento(mensajeDescuento);

        return response;
    }

    @Transactional
    public PagoResponse validarPago(Long id) {
        log.info("Validando pago ID: {}", id);

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNoEncontradoException(id));

        if (pago.getEstadoPago() == Estado.PAGADO) {
            throw new IllegalStateException("El pago ya está validado");
        }

        if (pago.getEstadoPago() == Estado.REEMBOLSO) {
            throw new IllegalStateException("No se puede validar un pago reembolsado");
        }

        // Cambiar estado a PAGADO
        pago.setEstadoPago(Estado.PAGADO);
        Pago pagoActualizado = pagoRepository.save(pago);

        log.info("Pago validado ID: {} - Estado: {}", id, pagoActualizado.getEstadoPago().getValor());

        //Notificar a ms-reserva que el pago fue confirmado
        notificarReserva(pagoActualizado);

        return pagoMapper.toResponse(pagoActualizado);
    }

    @Transactional
    public void notificarReserva(Pago pago) {
        try {
            log.info("Notificando a ms-reserva sobre el pago ID: {} para reserva: {}",
                    pago.getId(), pago.getIdReserva());

            ReservaPagoRequest request = new ReservaPagoRequest(pago.getId());

            // Llamar a ms-reserva para actualizar el estado de la reserva
            reservaClient.confirmarPagoReserva(pago.getIdReserva(), request);

            log.info("Reserva ID: {} notificada exitosamente sobre el pago ID: {}",
                    pago.getIdReserva(), pago.getId());

        } catch (FeignException e) {
            log.error("Error al notificar a ms-reserva sobre el pago ID: {} - Error: {}",
                    pago.getId(), e.getMessage());

            // El pago ya está confirmado, la reserva se actualizará en un reintento
        }
    }

    @Transactional
    public void eliminarPago(Long id) {
        log.info("Eliminando pago ID: {}", id);

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNoEncontradoException(id));

        // No permitir eliminar pagos en estado PAGADO o REEMBOLSO
        if (pago.getEstadoPago() == Estado.PAGADO || pago.getEstadoPago() == Estado.REEMBOLSO) {
            throw new IllegalStateException("No se puede eliminar un pago en estado: " +
                    pago.getEstadoPago().getValor());
        }

        pagoRepository.delete(pago);
        log.info("Pago eliminado ID: {}", id);
    }

    @Transactional
    public PagoResponse reembolsarPago(Long id) {
        log.info("Reembolsando pago ID: {}", id);

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNoEncontradoException(id));

        if (pago.getEstadoPago() != Estado.PAGADO) {
            throw new PagoNoReembolsadoException("Solo se puede reembolsar un pago en estado PAGADO");
        }

        pago.setEstadoPago(Estado.REEMBOLSO);
        Pago pagoActualizado = pagoRepository.save(pago);

        log.info("Pago reembolsado ID: {} - Estado: {}", id, pagoActualizado.getEstadoPago().getValor());

        return pagoMapper.toResponse(pagoActualizado);
    }

    @Transactional(readOnly = true)
    public EstadoResponse verEstadoPago(Long id) {
        log.info("Consultando estado del pago ID: {}", id);

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNoEncontradoException(id));

        EstadoResponse response = new EstadoResponse();
        response.setId(pago.getId());
        response.setEstadoPago(pago.getEstadoPago().getValor());

        return response;
    }

    @Transactional(readOnly = true)
    public MontoResponse verMontoPago(Long id) {
        log.info("Consultando monto del pago ID: {}", id);

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNoEncontradoException(id));

        MontoResponse response = new MontoResponse();
        response.setId(pago.getId());
        response.setMontoPago(pago.getMontoPago());

        return response;
    }

    @Transactional(readOnly = true)
    public List<Pago> obtenerPagosPorReserva(Long idReserva) {
        log.info("Consultando pagos para reserva ID: {}", idReserva);
        return pagoRepository.findByIdReserva(idReserva);
    }
}