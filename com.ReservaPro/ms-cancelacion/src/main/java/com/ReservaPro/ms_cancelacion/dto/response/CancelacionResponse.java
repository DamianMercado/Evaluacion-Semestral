package com.ReservaPro.ms_cancelacion.dto.response;

import com.ReservaPro.ms_cancelacion.model.EstadoReembolso;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Data
@Schema(description = "Respuesta con la información de una cancelación")
public class CancelacionResponse {

    @Schema(
            description = "ID de la cancelación",
            example = "1"
    )
    private Long idCancelacion;

    @Schema(
            description = "Motivo de la cancelación",
            example = "Cambio de planes del cliente"
    )
    private String motivo;

    @Schema(
            description = "Fecha de la cancelación",
            example = "10-06-2026"
    )
    private String fechaCancelacion;

    @Schema(
            description = "Estado del reembolso asociado a la cancelación",
            example = "PENDIENTE"
    )
    private EstadoReembolso estadoReembolso;
}