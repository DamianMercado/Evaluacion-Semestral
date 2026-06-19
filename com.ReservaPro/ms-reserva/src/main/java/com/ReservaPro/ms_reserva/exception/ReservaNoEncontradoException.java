package com.ReservaPro.ms_reserva.exception;

public class ReservaNoEncontradoException extends RuntimeException {

    public ReservaNoEncontradoException(Long id) {
        super("No se encontró ningún usuario con el id " + id);
    }
}
