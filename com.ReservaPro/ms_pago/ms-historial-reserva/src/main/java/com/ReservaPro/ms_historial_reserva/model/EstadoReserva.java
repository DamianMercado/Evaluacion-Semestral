package com.ReservaPro.ms_historial_reserva.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estados posibles de una reserva")
public enum EstadoReserva {

    PENDIENTE,
    CONFIRMADA,
    CANCELADA,
    COMPLETADA
}