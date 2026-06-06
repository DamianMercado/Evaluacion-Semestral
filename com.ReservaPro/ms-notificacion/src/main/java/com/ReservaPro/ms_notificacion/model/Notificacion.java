package com.ReservaPro.ms_notificacion.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notificaciones")

public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacion;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_reserva")
    private Long idReserva;

    @Column(name = "id_cancelacion")
    private Long idCancelacion;

    @Column(name = "mensaje", nullable = false, length = 200)
    private String mensaje;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;



    @Column(name = "leida", nullable = false)
    private Boolean leida;
}