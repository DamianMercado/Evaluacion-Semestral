package com.ReservaPro.ms_notificacion.exception;

public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
} // esta regla de negocio no puede estar vacia deve  deve cumplir antes de ser guadado