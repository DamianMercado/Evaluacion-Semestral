package com.ReservaPro.ms_reserva.exception;

public class ReservaNoEncontrado extends RuntimeException {

    public ReservaNoEncontrado(Long id) {
        super("No se encontró ningún usuario con el id " + id);
    }
}
