package com.ReservaPro.ms_disponibilidad.dto.request;

import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Schema(description = "Datos necesarios para crear o actualizar una disponibilidad")
public class DisponibilidadRequest {

    @Schema(
            description = "Fecha de la disponibilidad",
            example = "2026-06-10"
    )
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @Schema(
            description = "Hora de inicio",
            example = "09:00"
    )
    @NotNull(message = "La hora inicio es obligatoria")
    private LocalTime horaInicio;

    @Schema(
            description = "Hora de término",
            example = "18:00"
    )
    @NotNull(message = "La hora fin es obligatoria")
    private LocalTime horaFin;

    @Schema(
            description = "Cantidad de cupos disponibles",
            example = "8"
    )
    @NotNull(message = "Los cupos disponibles son obligatorios")
    @PositiveOrZero(message = "Los cupos disponibles no pueden ser negativos")
    private Integer cuposDisponibles;

    @Schema(
            description = "Cantidad total de cupos",
            example = "10"
    )
    @NotNull(message = "Los cupos totales son obligatorios")
    @Positive(message = "Los cupos totales deben ser mayores a cero")
    private Integer cuposTotales;

    @Schema(
            description = "Estado de la disponibilidad",
            example = "DISPONIBLE"
    )
    private Disponibilidad.EstadoDisponibilidad estado;

    @Schema(
            description = "Observaciones adicionales",
            example = "Disponible solo para fines de semana"
    )
    private String observacion;

    @Schema(
            description = "Indica si la disponibilidad está activa",
            example = "true"
    )
    private Boolean activo;
}