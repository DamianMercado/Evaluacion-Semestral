package com.ReservaPro.ms_notificacion.service;

import com.ReservaPro.ms_notificacion.dto.request.NotificacionRequest;
import com.ReservaPro.ms_notificacion.dto.response.NotificacionResponse;
import com.ReservaPro.ms_notificacion.exception.NotificacionNoEncontradaException;
import com.ReservaPro.ms_notificacion.mapper.NotificacionMapper;
import com.ReservaPro.ms_notificacion.model.Notificacion;
import com.ReservaPro.ms_notificacion.repository.NotificacionRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private static final Logger log =
            LoggerFactory.getLogger(NotificacionService.class);

    private final NotificacionRepository notificacionRepository;
    private final NotificacionMapper notificacionMapper;

    public List<NotificacionResponse> obtener() {
        log.info("Obteniendo todas las notificaciones");
        return notificacionMapper.toResponseList(notificacionRepository.findAll());
    }

    public NotificacionResponse obtenerPorId(Long id) {
        log.info("Buscando notificación con ID: {}", id);
        return notificacionMapper.toResponse(notificacionRepository
                .findById(id)
                .orElseThrow(() -> new NotificacionNoEncontradaException(id)));
    }

    public NotificacionResponse crear(NotificacionRequest request) {
        log.info("Creando notificación para usuario ID: {}", request.getIdUsuario());

        Notificacion notificacion = notificacionMapper.toEntity(request);

        return notificacionMapper.toResponse(
                notificacionRepository.save(notificacion)
        );
    }

    public void eliminar(Long id) {
        log.info("Eliminando notificación ID: {}", id);

        if (!notificacionRepository.existsById(id)) {
            throw new NotificacionNoEncontradaException(id);
        }

        notificacionRepository.deleteById(id);
    }

    public NotificacionResponse actualizar(Long id, NotificacionRequest request) {
        log.info("Actualizando notificación ID: {}", id);

        Notificacion notificacionExistente = notificacionRepository
                .findById(id)
                .orElseThrow(() -> new NotificacionNoEncontradaException(id));

        notificacionExistente.setIdUsuario(request.getIdUsuario());
        notificacionExistente.setIdReserva(request.getIdReserva());
        notificacionExistente.setIdCancelacion(request.getIdCancelacion());
        notificacionExistente.setMensaje(request.getMensaje());
        notificacionExistente.setTipo(request.getTipo());
        notificacionExistente.setLeida(request.getLeida());

        return notificacionMapper.toResponse(
                notificacionRepository.save(notificacionExistente)
        );
    }
}