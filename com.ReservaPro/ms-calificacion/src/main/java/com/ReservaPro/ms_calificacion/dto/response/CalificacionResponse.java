package com.ReservaPro.ms_calificacion.dto.response;

import com.ReservaPro.ms_calificacion.enums.EstadoCalificacion;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CalificacionResponse {
    private Long id;
    private Long idReserva;
    private Long idUsuario;
    private Integer puntuacion;
    private String comentario;
    private EstadoCalificacion estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}