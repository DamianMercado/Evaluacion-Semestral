package com.ReservaPro.ms_disponibilidad.exception;

public class DisponibilidadNotFoundException
        extends RuntimeException {

    public DisponibilidadNotFoundException(Long id) {
        super("Disponibilidad no encontrada con id: " + id);
    }
}