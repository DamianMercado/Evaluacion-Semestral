package com.ReservaPro.ms_historial_reserva.dto.request;


import com.ReservaPro.ms_historial_reserva.model.EstadoReserva;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class HistorialReservaRequest {

    @NotNull(message = "El idReserva es obligatorio")
    private Long idReserva;

    @NotNull(message = "El estadoAnterior es obligatorio")
    private EstadoReserva estadoAnterior;

    @NotNull(message = "El estadoNuevo es obligatorio")
    private EstadoReserva estadoNuevo;

    private String observacion;
}