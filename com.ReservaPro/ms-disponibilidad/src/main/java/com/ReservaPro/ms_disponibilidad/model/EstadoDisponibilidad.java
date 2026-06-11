package com.ReservaPro.ms_disponibilidad.model;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Estados posibles de la disponibilidad",
        example = "DISPONIBLE"
)
public enum EstadoDisponibilidad {
    DISPONIBLE,
    BLOQUEADO
}