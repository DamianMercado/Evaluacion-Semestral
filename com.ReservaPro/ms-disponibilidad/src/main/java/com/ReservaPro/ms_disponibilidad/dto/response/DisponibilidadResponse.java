package com.ReservaPro.ms_disponibilidad.dto.response;

import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@Schema(description = "Respuesta con la información de una disponibilidad")
public class DisponibilidadResponse {

    @Schema(
            description = "ID de la disponibilidad",
            example = "1"
    )
    private Long idDisponibilidad;

    @Schema(
            description = "Fecha de la disponibilidad",
            example = "2026-06-10"
    )
    private LocalDate fecha;

    @Schema(
            description = "Hora de inicio",
            example = "09:00"
    )
    private LocalTime horaInicio;

    @Schema(
            description = "Hora de término",
            example = "18:00"
    )
    private LocalTime horaFin;

    @Schema(
            description = "Cantidad de cupos disponibles",
            example = "8"
    )
    private Integer cuposDisponibles;

    @Schema(
            description = "Cantidad total de cupos",
            example = "10"
    )
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

    @Schema(
            description = "Fecha y hora de creación del registro",
            example = "2026-06-10T15:30:00"
    )
    private LocalDateTime fechaCreacion;
}