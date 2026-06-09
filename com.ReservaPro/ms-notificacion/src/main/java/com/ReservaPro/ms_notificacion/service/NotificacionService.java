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

        return notificacionMapper.toResponseList(
                notificacionRepository.findAll()
        );
    }

    public NotificacionResponse obtenerPorId(Long id) {

        log.info("Buscando notificación con ID: {}", id);

        return notificacionMapper.toResponse(
                notificacionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new NotificacionNoEncontradaException(id)
                        )
        );
    }

    public NotificacionResponse crear(NotificacionRequest request) {

        log.info(
                "Creando notificación para usuario ID: {}",
                request.getIdUsuario()
        );

        return notificacionMapper.toResponse(
                notificacionRepository.save(
                        notificacionMapper.toEntity(request)
                )
        );
    }

    public NotificacionResponse actualizar(
            Long id,
            NotificacionRequest request) {

        log.info("Actualizando notificación ID: {}", id);

        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() ->
                        new NotificacionNoEncontradaException(id)
                );

        notificacion.setIdUsuario(
                request.getIdUsuario()
        );

        notificacion.setIdReserva(
                request.getIdReserva()
        );

        notificacion.setIdCancelacion(
                request.getIdCancelacion()
        );

        notificacion.setMensaje(
                request.getMensaje()
        );

        notificacion.setTipo(
                request.getTipo()
        );

        notificacion.setLeida(
                request.getLeida()
        );

        Notificacion actualizada =
                notificacionRepository.save(notificacion);

        log.info(
                "Notificación actualizada correctamente ID: {}",
                id
        );

        return notificacionMapper.toResponse(actualizada);
    }

    public void eliminar(Long id) {

        log.info("Eliminando notificación ID: {}", id);

        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() ->
                        new NotificacionNoEncontradaException(id)
                );

        notificacionRepository.delete(notificacion);

        log.info(
                "Notificación eliminada correctamente ID: {}",
                id
        );
    }
}