package com.ReservaPro.ms_historial_reserva.dto.response;


import com.ReservaPro.ms_historial_reserva.model.EstadoReserva;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistorialReservaResponse {

    private Long idHistorial;

    private Long idReserva;

    private EstadoReserva estadoAnterior;

    private EstadoReserva estadoNuevo;

    private LocalDateTime fechaCambio;

    private String observacion;
}