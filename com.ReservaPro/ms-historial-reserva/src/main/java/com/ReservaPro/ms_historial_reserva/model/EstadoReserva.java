package com.ReservaPro.ms_historial_reserva.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Estados posibles de una reserva")
public enum EstadoReserva {

    PENDIENTE,
    CONFIRMADA,
    CANCELADA,
    COMPLETADA
}