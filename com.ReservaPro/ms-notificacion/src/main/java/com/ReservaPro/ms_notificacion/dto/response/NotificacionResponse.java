package com.ReservaPro.ms_notificacion.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificacionResponse {

    private Long idNotificacion;
    private Long idUsuario;
    private Long idReserva;
    private Long idCancelacion;
    private String mensaje;
    private String tipo;
    private LocalDateTime fechaEnvio;
    private Boolean leida;
}