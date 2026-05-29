package com.ReservaPro.ms_disponibilidad.dto.request;

import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DisponibilidadRequest {

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora fin es obligatoria")
    private LocalTime horaFin;

    @NotNull(message = "Los cupos disponibles son obligatorios")
    @PositiveOrZero
    private Integer cuposDisponibles;

    @NotNull(message = "Los cupos totales son obligatorios")
    @Positive
    private Integer cuposTotales;
    private Disponibilidad.EstadoDisponibilidad estado;
    private String observacion;
    private Boolean activo;
}