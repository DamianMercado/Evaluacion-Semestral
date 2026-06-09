package com.ReservaPro.ms_notificacion.model;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "notificaciones")
@Schema(description = "Entidad que representa una notificación")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la notificación", example = "1")
    private Long idNotificacion;

    @Column(name = "id_usuario", nullable = false)
    @Schema(description = "ID del usuario", example = "10")
    private Long idUsuario;

    @Column(name = "id_reserva")
    @Schema(description = "ID de la reserva", example = "5")
    private Long idReserva;

    @Column(name = "id_cancelacion")
    @Schema(description = "ID de la cancelación", example = "2")
    private Long idCancelacion;

    @Column(name = "mensaje", nullable = false, length = 200)
    @Schema(description = "Mensaje de la notificación", example = "Reserva confirmada")
    private String mensaje;

    @Column(name = "tipo", nullable = false, length = 20)
    @Schema(description = "Tipo de notificación", example = "EMAIL")
    private String tipo;

    @Column(name = "leida", nullable = false)
    @Schema(description = "Indica si la notificación fue leída", example = "false")
    private Boolean leida;
}