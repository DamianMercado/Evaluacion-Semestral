package com.ReservaPro.ms_notificacion.exception;

public class NotificacionNoEncontradaException extends RuntimeException {

    public NotificacionNoEncontradaException(Long id) {
        super("No se ha encontrado la notificación con el id: " + id);
    }
}