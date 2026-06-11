package com.ReservaPro.ms_cancelacion.dto.request;

import com.ReservaPro.ms_cancelacion.model.EstadoReembolso;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

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
    @NotBlank(message = "La fecha de cancelacion es obligatoria")
    private String fechaCancelacion;

    @Schema(
            description = "Estado del reembolso asociado a la cancelación",
            example = "PENDIENTE"
    )
    private EstadoReembolso estadoReembolso;
}