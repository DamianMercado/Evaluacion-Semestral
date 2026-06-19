package com.ReservaPro.ms_historial_reserva.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historial_reserva")
@Schema(description = "Entidad que representa el historial de cambios de una reserva")
public class HistorialReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del historial", example = "1")
    private Long idHistorial;

    @Column(name = "id_reserva", nullable = false)
    @Schema(description = "ID de la reserva", example = "10")
    private Long idReserva;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_anterior", nullable = false, length = 50)
    @Schema(
            description = "Estado anterior de la reserva",
            implementation = EstadoReserva.class
    )
    private EstadoReserva estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_nuevo", nullable = false, length = 50)
    @Schema(
            description = "Nuevo estado de la reserva",
            implementation = EstadoReserva.class
    )
    private EstadoReserva estadoNuevo;

    @Column(name = "fecha_cambio", nullable = false)
    @Schema(
            description = "Fecha y hora en que ocurrió el cambio",
            example = "2026-06-14T15:30:00"
    )
    private LocalDateTime fechaCambio;

    @Column(name = "observacion", length = 255)
    @Schema(
            description = "Observación del cambio realizado",
            example = "Reserva cancelada por el usuario"
    )
    private String observacion;

    @PrePersist
    public void prePersist() {

        if (this.fechaCambio == null) {
            this.fechaCambio = LocalDateTime.now();
        }
    }
}