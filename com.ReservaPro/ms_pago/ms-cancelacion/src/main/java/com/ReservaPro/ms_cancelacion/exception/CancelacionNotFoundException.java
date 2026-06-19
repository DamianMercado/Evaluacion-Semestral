package com.ReservaPro.ms_cancelacion.exception;

public class CancelacionNotFoundException extends RuntimeException {

    public CancelacionNotFoundException(Long id) {
        super("Cancelacion no encontrada con id: " + id);
    }
}