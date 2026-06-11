package com.ReservaPro.ms_cancelacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

    @NotBlank(message = "La fecha de cancelación es obligatoria")
    @Column(nullable = false)
    @Schema(description = "Fecha de la cancelación", example = "2026-06-10")
    private String fechaCancelacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(
            description = "Estado del reembolso",
            example = "PENDIENTE"
    )
    private EstadoReembolso estadoReembolso;

    public void cancelarReserva(String motivo) {
        this.motivo = motivo;
        this.estadoReembolso = EstadoReembolso.PENDIENTE;
    }

    public boolean procesarReembolso() {
        this.estadoReembolso = EstadoReembolso.REEMBOLSADO;
        return true;
    }
}