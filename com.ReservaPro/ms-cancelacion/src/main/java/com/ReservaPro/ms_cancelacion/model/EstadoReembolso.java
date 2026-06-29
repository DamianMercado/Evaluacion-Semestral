package com.ReservaPro.ms_cancelacion.model;
// Define el paquete donde se encuentra el Enum EstadoReembolso

import io.swagger.v3.oas.annotations.media.Schema;
// Permite documentar el Enum y sus valores en Swagger

@Schema(
        description = "Estado del reembolso asociado a la cancelación"
)
// Descripción general del Enum que aparecerá en Swagger

public enum EstadoReembolso {
    // Declaración del Enum EstadoReembolso

    @Schema(description = "El reembolso aún está en proceso")
    // Describe este valor en Swagger

    PENDIENTE,
    // Indica que el reembolso todavía no ha sido realizado

    @Schema(description = "El reembolso fue realizado exitosamente")
    // Describe este valor en Swagger

    REEMBOLSADO,
    // Indica que el dinero ya fue devuelto al cliente

    @Schema(description = "La solicitud de reembolso fue rechazada")
    // Describe este valor en Swagger

    RECHAZADO
    // Indica que la solicitud de devolución fue rechazada
}