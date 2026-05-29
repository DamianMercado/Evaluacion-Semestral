package com.ReservaPro.ms_disponibilidad.dto.response;

import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class DisponibilidadResponse {

    private Long idDisponibilidad;

    private LocalDate fecha;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    private Integer cuposDisponibles;

    private Integer cuposTotales;

    private Disponibilidad.EstadoDisponibilidad estado;

    private String observacion;

    private Boolean activo;

    private LocalDateTime fechaCreacion;
}