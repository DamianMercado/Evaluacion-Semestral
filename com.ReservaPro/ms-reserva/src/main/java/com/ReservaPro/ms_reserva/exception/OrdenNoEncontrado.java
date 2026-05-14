package com.ReservaPro.ms_reserva.exception;

public class OrdenNoEncontrado extends RuntimeException {

    public OrdenNoEncontrado(Long id) {
        super("No se encontró ningún usuario con el id " + id);
    }
}
