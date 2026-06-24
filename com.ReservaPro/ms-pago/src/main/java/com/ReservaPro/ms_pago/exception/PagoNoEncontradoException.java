package com.ReservaPro.ms_pago.exception;

public class PagoNoEncontradoException extends RuntimeException {

    private static final String MENSAJE_POR_DEFECTO = "Pago no encontrado con ID: ";

    public PagoNoEncontradoException(Long id) {
        super(MENSAJE_POR_DEFECTO + id);
    }

    public PagoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}