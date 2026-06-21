package com.ReservaPro.ms_notificacion.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificacionResponse {

    private Long idNotificacion;
    private Long idUsuario;
    private Long idReserva;
    private Long idCancelacion;
    private String mensaje;
    private String tipo;
    private LocalDate fechaEnvio;
    private Boolean leida;
}