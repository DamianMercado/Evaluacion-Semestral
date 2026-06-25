package com.ReservaPro.ms_reserva.service;

import com.ReservaPro.ms_reserva.client.*;
import com.ReservaPro.ms_reserva.dto.request.ReservaPagoRequest;
import com.ReservaPro.ms_reserva.dto.request.ReservaRequest;
import com.ReservaPro.ms_reserva.dto.response.ReservaCompletaResponse;
import com.ReservaPro.ms_reserva.dto.response.ReservaResponse;
import com.ReservaPro.ms_reserva.enums.EstadoReserva;
import com.ReservaPro.ms_reserva.exception.ReservaNoEncontradaException;
import com.ReservaPro.ms_reserva.mapper.ReservaMapper;
import com.ReservaPro.ms_reserva.model.Reserva;
import com.ReservaPro.ms_reserva.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    // Feign Clients para orquestación
    private final UsuarioClient usuarioClient;
    private final GestionServicioClient gestionServicioClient;
    private final PagoClient pagoClient;
    private final CalificacionClient calificacionClient;
    private final CancelacionClient cancelacionClient;

    // ========== CRUD BÁSICO ==========

    @Transactional(readOnly = true)
    public List<ReservaResponse> obtenerTodos() {
        log.info("Obteniendo todas las reservas");
        return reservaMapper.toResponseList(reservaRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ReservaResponse obtenerPorId(Long id) {
        log.info("Buscando reserva con ID: {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));
        return reservaMapper.toResponse(reserva);
    }

    @Transactional
    public ReservaResponse crear(ReservaRequest request) {
        log.info("Creando nueva reserva para usuario: {}", request.getIdUsuario());

        // Validar que el usuario existe
        validarUsuario(request.getIdUsuario());

        // Validar que el servicio existe
        validarServicio(request.getIdGestionServicio());

        Reserva reserva = reservaMapper.toEntity(request);

        //Siempre se crea como PENDIENTE_PAGO
        reserva.setEstadoReserva(EstadoReserva.PENDIENTE_PAGO);

        // Validar precios
        if (request.getPrecioFinal() > request.getPrecioReserva()) {
            throw new IllegalArgumentException("El precio final no puede ser mayor al precio de la reserva");
        }

        Reserva guardada = reservaRepository.save(reserva);
        log.info("Reserva creada correctamente con ID: {} - Estado: {}",
                guardada.getId(),
                guardada.getEstadoReserva().getValor());

        return reservaMapper.toResponse(guardada);
    }

    @Transactional
    public ReservaResponse actualizar(Long id, ReservaRequest request) {
        log.info("Actualizando reserva con ID: {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));

        // Validar que no esté en estado final
        if (reserva.getEstadoReserva() == EstadoReserva.COMPLETADA ||
                reserva.getEstadoReserva() == EstadoReserva.CANCELADA) {
            throw new IllegalStateException(
                    "No se puede modificar una reserva en estado " +
                            reserva.getEstadoReserva().getValor()
            );
        }

        // Solo actualizar si está PENDIENTE_PAGO
        if (reserva.getEstadoReserva() != EstadoReserva.PENDIENTE_PAGO) {
            throw new IllegalStateException(
                    "Solo se pueden actualizar reservas en estado PENDIENTE_PAGO"
            );
        }

        reserva.setFechaReserva(request.getFechaReserva());
        reserva.setPrecioReserva(request.getPrecioReserva());
        reserva.setDescuentoAplicado(request.getDescuentoAplicado());
        reserva.setPrecioFinal(request.getPrecioFinal());
        reserva.setIdGestionServicio(request.getIdGestionServicio());
        reserva.setIdPromocion(request.getIdPromocion());

        Reserva actualizada = reservaRepository.save(reserva);
        log.info("Reserva actualizada correctamente con ID: {}", id);

        return reservaMapper.toResponse(actualizada);
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando reserva con ID: {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));

        // No permitir eliminar reservas en estados finales
        if (reserva.getEstadoReserva() == EstadoReserva.COMPLETADA) {
            throw new IllegalStateException("No se puede eliminar una reserva completada");
        }

        reservaRepository.delete(reserva);
        log.info("Reserva eliminada correctamente con ID: {}", id);
    }

    //FLUJO DE PAGO

    @Transactional
    public ReservaResponse confirmarPago(Long id, ReservaPagoRequest request) {
        log.info("Confirmando pago para reserva ID: {} con pago ID: {}", id, request.getIdPago());

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));

        // Validar que el pago existe en ms-pago
        validarPago(request.getIdPago());

        // Solo se puede pagar si está PENDIENTE_PAGO
        if (reserva.getEstadoReserva() != EstadoReserva.PENDIENTE_PAGO) {
            throw new IllegalStateException(
                    "La reserva no está en estado PENDIENTE_PAGO. Estado actual: " +
                            reserva.getEstadoReserva().getValor()
            );
        }

        // Asignar ID de pago
        reserva.setIdPago(request.getIdPago());

        // 🔥 CRÍTICO: Cambiar estado a PAGADO
        reserva.setEstadoReserva(EstadoReserva.PAGADO);

        Reserva actualizada = reservaRepository.save(reserva);
        log.info("Pago confirmado para reserva ID: {} - Estado: {}",
                id,
                actualizada.getEstadoReserva().getValor());

        return reservaMapper.toResponse(actualizada);
    }

    @Transactional
    public ReservaResponse confirmarReserva(Long id) {
        log.info("Confirmando reserva ID: {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));

        // Solo se puede confirmar si está PAGADO
        if (reserva.getEstadoReserva() != EstadoReserva.PAGADO) {
            throw new IllegalStateException(
                    "La reserva no está en estado PAGADO. Estado actual: " +
                            reserva.getEstadoReserva().getValor()
            );
        }

        reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);

        Reserva actualizada = reservaRepository.save(reserva);
        log.info("Reserva confirmada ID: {}", id);

        return reservaMapper.toResponse(actualizada);
    }

    @Transactional
    public ReservaResponse cancelarReserva(Long id) {
        log.info("Cancelando reserva ID: {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));

        // Verificar transición permitida
        if (!reserva.getEstadoReserva().puedeTransicionarA(EstadoReserva.CANCELADA)) {
            throw new IllegalStateException(
                    "No se puede cancelar una reserva en estado " +
                            reserva.getEstadoReserva().getValor()
            );
        }

        reserva.setEstadoReserva(EstadoReserva.CANCELADA);

        Reserva actualizada = reservaRepository.save(reserva);
        log.info("Reserva cancelada ID: {}", id);

        return reservaMapper.toResponse(actualizada);
    }

    @Transactional
    public ReservaResponse completarReserva(Long id) {
        log.info("Completando reserva ID: {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));

        // Solo se puede completar si está CONFIRMADA
        if (reserva.getEstadoReserva() != EstadoReserva.CONFIRMADA) {
            throw new IllegalStateException(
                    "La reserva no está en estado CONFIRMADA. Estado actual: " +
                            reserva.getEstadoReserva().getValor()
            );
        }

        reserva.setEstadoReserva(EstadoReserva.COMPLETADA);

        Reserva actualizada = reservaRepository.save(reserva);
        log.info("Reserva completada ID: {}", id);

        return reservaMapper.toResponse(actualizada);
    }

    //MÉTODOS DE CONSULTA

    @Transactional(readOnly = true)
    public List<ReservaResponse> obtenerPorUsuario(Long idUsuario) {
        log.info("Buscando reservas del usuario: {}", idUsuario);
        return reservaMapper.toResponseList(
                reservaRepository.findByIdUsuario(idUsuario)
        );
    }

    @Transactional(readOnly = true)
    public List<ReservaResponse> obtenerPorEstado(EstadoReserva estado) {
        log.info("Buscando reservas por estado: {}", estado.getValor());
        return reservaMapper.toResponseList(
                reservaRepository.findByEstadoReserva(estado)
        );
    }

    @Transactional(readOnly = true)
    public ReservaCompletaResponse obtenerReservaCompleta(Long id) {
        log.info("Obteniendo reserva completa con ID: {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));

        ReservaCompletaResponse response = new ReservaCompletaResponse();
        response.setReserva(reservaMapper.toResponse(reserva));

        // Obtener datos de otros microservicios via Feign
        try {
            response.setUsuario(usuarioClient.obtenerUsuarioPorId(reserva.getIdUsuario()));
        } catch (Exception e) {
            log.warn("No se pudo obtener usuario para reserva {}: {}", id, e.getMessage());
        }

        try {
            response.setServicio(gestionServicioClient.obtenerServicioPorId(reserva.getIdGestionServicio()));
        } catch (Exception e) {
            log.warn("No se pudo obtener servicio para reserva {}: {}", id, e.getMessage());
        }

        if (reserva.getIdPago() != null) {
            try {
                response.setPago(pagoClient.obtenerPagoPorId(reserva.getIdPago()));
            } catch (Exception e) {
                log.warn("No se pudo obtener pago para reserva {}: {}", id, e.getMessage());
            }
        }

        if (reserva.getIdCalificacion() != null) {
            try {
                response.setCalificacion(calificacionClient.obtenerCalificacionPorId(reserva.getIdCalificacion()));
            } catch (Exception e) {
                log.warn("No se pudo obtener calificación para reserva {}: {}", id, e.getMessage());
            }
        }

        return response;
    }

    //MÉTODOS DE VALIDACIÓN

    private void validarUsuario(Long idUsuario) {
        try {
            usuarioClient.obtenerUsuarioPorId(idUsuario);
        } catch (Exception e) {
            log.error("Error al validar usuario ID {}: {}", idUsuario, e.getMessage());
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + idUsuario);
        }
    }

    private void validarServicio(Long idServicio) {
        try {
            gestionServicioClient.obtenerServicioPorId(idServicio);
        } catch (Exception e) {
            log.error("Error al validar servicio ID {}: {}", idServicio, e.getMessage());
            throw new IllegalArgumentException("Servicio no encontrado con ID: " + idServicio);
        }
    }

    private void validarPago(Long idPago) {
        try {
            pagoClient.obtenerPagoPorId(idPago);
        } catch (Exception e) {
            log.error("Error al validar pago ID {}: {}", idPago, e.getMessage());
            throw new IllegalArgumentException("Pago no encontrado con ID: " + idPago);
        }
    }
}