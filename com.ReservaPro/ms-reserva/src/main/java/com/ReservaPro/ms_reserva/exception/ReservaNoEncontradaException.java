package com.ReservaPro.ms_reserva.exception;

public class ReservaNoEncontradaException extends RuntimeException {

    private static final String MENSAJE_POR_DEFECTO = "Reserva no encontrada con ID: ";

    public ReservaNoEncontradaException(Long id) {
        super(MENSAJE_POR_DEFECTO + id);
    }

    public ReservaNoEncontradaException(String mensaje) {
        super(mensaje);
    }

    public ReservaNoEncontradaException(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }
}