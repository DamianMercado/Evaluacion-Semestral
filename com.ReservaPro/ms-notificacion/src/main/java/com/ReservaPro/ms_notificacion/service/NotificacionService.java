package com.ReservaPro.ms_notificacion.service;

import com.ReservaPro.ms_notificacion.client.CancelacionClient;
import com.ReservaPro.ms_notificacion.dto.request.NotificacionRequest;
import com.ReservaPro.ms_notificacion.dto.response.NotificacionResponse;
import com.ReservaPro.ms_notificacion.exception.NotificacionNoEncontradaException;
import com.ReservaPro.ms_notificacion.exception.ReglaNegocioException;
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
            LoggerFactory.getLogger(NotificacionService.class);// sirve para mostrar msn en consola por ej crea nuscar ect

    private final NotificacionRepository notificacionRepository;// trabaja con la base datos
    private final NotificacionMapper notificacionMapper;
    private final CancelacionClient cancelacionClient;// comunica con otro microservicio

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
                                new NotificacionNoEncontradaException(id))
        );
    }

    public NotificacionResponse crear(NotificacionRequest request) {

        log.info(
                "Creando notificación para usuario ID: {}",
                request.getIdUsuario()
        );

        validarNotificacion(request);

        Notificacion notificacion =
                notificacionMapper.toEntity(request);

        return notificacionMapper.toResponse(
                notificacionRepository.save(notificacion)
        );
    }

    public NotificacionResponse actualizar(
            Long id,
            NotificacionRequest request) {

        log.info("Actualizando notificación ID: {}", id);

        Notificacion notificacionExistente =
                notificacionRepository.findById(id)
                        .orElseThrow(() ->
                                new NotificacionNoEncontradaException(id));
                                   //si lo encuentra lo devuelve si no lansa exepcion

        validarNotificacion(request);

        notificacionExistente.setIdUsuario(
                request.getIdUsuario()
        );

        notificacionExistente.setIdReserva(
                request.getIdReserva()
        );

        notificacionExistente.setIdCancelacion(
                request.getIdCancelacion()
        );

        notificacionExistente.setMensaje(
                request.getMensaje()
        );

        notificacionExistente.setTipo(
                request.getTipo()
        );

        notificacionExistente.setLeida(
                request.getLeida()
        );

        return notificacionMapper.toResponse(
                notificacionRepository.save(notificacionExistente)
        );
    }

    public void eliminar(Long id) {

        log.info("Eliminando notificación ID: {}", id);

        if (!notificacionRepository.existsById(id)) {
            throw new NotificacionNoEncontradaException(id);
        }    //verifica si exite elimanar si no lanza exepcion

        notificacionRepository.deleteById(id);

        log.info(
                "Notificación eliminada correctamente ID: {}",
                id
        );
    }

    private void validarNotificacion(
            NotificacionRequest request) {  //metodo privado para validar negocio
        //revisa mensaje no venga vacio

        if (request.getMensaje() == null
                || request.getMensaje().isBlank()) {

            throw new ReglaNegocioException(
                    "El mensaje de la notificación es obligatorio"
            );
        }

        if (request.getTipo() == null
                || request.getTipo().isBlank()) {//revisa que no venga vacio

            throw new ReglaNegocioException(
                    "El tipo de la notificación es obligatorio"
            );
        }

        if (request.getIdReserva() == null
                && request.getIdCancelacion() == null) { //revisa notificacion este asocida  notificacion o una reserva

            throw new ReglaNegocioException(
                    "La notificación debe estar asociada a una reserva o cancelación"
            );
        }
    }
}