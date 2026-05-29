package com.ReservaPro.ms_notificacion.service;

import com.ReservaPro.ms_notificacion.dto.request.NotificacionRequest;
import com.ReservaPro.ms_notificacion.dto.response.NotificacionResponse;
import com.ReservaPro.ms_notificacion.exception.NotificacionNoEncontradaException;
import com.ReservaPro.ms_notificacion.mapper.NotificacionMapper;
import com.ReservaPro.ms_notificacion.model.Notificacion;
import com.ReservaPro.ms_notificacion.repository.NotificacionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final NotificacionMapper notificacionMapper;

    public List<NotificacionResponse> obtener() {

        return notificacionMapper.toResponseList(
                notificacionRepository.findAll()
        );
    }

    public NotificacionResponse obtenerPorId(Long id) {

        return notificacionMapper.toResponse(
                notificacionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new NotificacionNoEncontradaException(id)
                        )
        );
    }

    public NotificacionResponse crear(NotificacionRequest request) {

        return notificacionMapper.toResponse(
                notificacionRepository.save(
                        notificacionMapper.toEntity(request)
                )
        );
    }

    public NotificacionResponse actualizar(Long id,
                                           NotificacionRequest request) {

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


        return notificacionMapper.toResponse(
                notificacionRepository.save(notificacion)
        );
    }

    public void eliminar(Long id) {

        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() ->
                        new NotificacionNoEncontradaException(id)
                );

        notificacionRepository.delete(notificacion);
    }
}