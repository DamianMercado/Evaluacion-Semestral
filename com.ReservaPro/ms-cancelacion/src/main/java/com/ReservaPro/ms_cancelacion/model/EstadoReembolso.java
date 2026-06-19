package com.ReservaPro.ms_cancelacion.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Estado del reembolso asociado a la cancelación"
)
public enum EstadoReembolso {

    @Schema(description = "El reembolso aún está en proceso")
    PENDIENTE,

    @Schema(description = "El reembolso fue realizado exitosamente")
    REEMBOLSADO,

    @Schema(description = "La solicitud de reembolso fue rechazada")
    RECHAZADO
}