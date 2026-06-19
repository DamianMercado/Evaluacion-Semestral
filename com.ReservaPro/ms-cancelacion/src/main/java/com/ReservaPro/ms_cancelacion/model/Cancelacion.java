package com.ReservaPro.ms_cancelacion.model;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cancelaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa una cancelación de reserva")
public class Cancelacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la cancelación", example = "1")
    private Long idCancelacion;

    @NotBlank(message = "El motivo es obligatorio")
    @Column(nullable = false)
    @Schema(description = "Motivo de la cancelación", example = "Cambio de planes")
    private String motivo;

    @NotNull(message = "La fecha de cancelación es obligatoria")
    @Column(nullable = false)
    @Schema(description = "Fecha de la cancelación", example = "2026-06-10")
    private LocalDate fechaCancelacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(
            description = "Estado del reembolso",
            example = "PENDIENTE"
    )
    private EstadoReembolso estadoReembolso;


    @Column(name = "id_reserva", nullable = false)
    @Schema(description = "ID de la reserva asociada", example = "123")
    private Long idReserva;
}