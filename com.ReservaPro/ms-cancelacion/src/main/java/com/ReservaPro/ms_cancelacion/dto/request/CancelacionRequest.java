package com.ReservaPro.ms_cancelacion.dto.request;

import com.ReservaPro.ms_cancelacion.model.EstadoReembolso;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Datos necesarios para crear o actualizar una cancelación")
public class CancelacionRequest {

    @Schema(
            description = "Motivo de la cancelación",
            example = "Cambio de planes del cliente"
    )
    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @Schema(
            description = "Fecha en que se realiza la cancelación",
            example = "2026-06-10"
    )
    @NotNull(message = "La fecha de cancelación es obligatoria")
    private LocalDate fechaCancelacion;

    @Schema(
            description = "Estado del reembolso asociado a la cancelación",
            example = "PENDIENTE"
    )
    private EstadoReembolso estadoReembolso;

    @Schema(
            description = "ID de la reserva asociada a la cancelación",
            example = "123"
    )
    private Long idReserva;
}