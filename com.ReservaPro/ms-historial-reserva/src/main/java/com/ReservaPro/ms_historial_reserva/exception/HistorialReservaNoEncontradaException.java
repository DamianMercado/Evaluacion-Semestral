package com.ReservaPro.ms_historial_reserva.exception;


public class HistorialReservaNoEncontradaException extends RuntimeException {

    public HistorialReservaNoEncontradaException(Long id) {
        super("No se ha encontrado el historial de reserva con el id: " + id);
    }
}