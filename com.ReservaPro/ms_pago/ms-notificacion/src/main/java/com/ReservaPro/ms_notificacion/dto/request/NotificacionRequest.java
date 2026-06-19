package com.ReservaPro.ms_notificacion.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data

public class NotificacionRequest {

    @NotNull(message = "El idUsuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El idReserva es obligatorio")
    private Long idReserva;

    @NotNull(message = "El idCancelacion es obligatorio")
    private Long idCancelacion;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotNull(message = "El campo leida es obligatorio")
    private Boolean leida;



}