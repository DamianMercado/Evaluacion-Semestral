package com.ReservaPro.ms_historial_reserva.dto.request;

import com.ReservaPro.ms_historial_reserva.model.EstadoReserva;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Schema(description = "Datos necesarios para crear o actualizar un historial de reserva")
public class HistorialReservaRequest {

    @NotNull(message = "El idReserva es obligatorio")
    @Schema(description = "ID de la reserva", example = "10")
    private Long idReserva;

    @NotNull(message = "El estadoAnterior es obligatorio")
    @Schema(
            description = "Estado anterior de la reserva",
            example = "PENDIENTE"
    )
    private EstadoReserva estadoAnterior;

    @NotNull(message = "El estadoNuevo es obligatorio")
    @Schema(
            description = "Nuevo estado de la reserva",
            example = "CONFIRMADA"
    )
    private EstadoReserva estadoNuevo;

    @Size(
            max = 255,
            message = "La observación no puede superar los 255 caracteres"
    )
    @Schema(
            description = "Observación del cambio realizado",
            example = "La reserva fue confirmada por el administrador"
    )
    private String observacion;
}