package com.ReservaPro.ms_cancelacion.dto.response;
// Define el paquete donde se encuentra el DTO Response

import com.ReservaPro.ms_cancelacion.model.EstadoReembolso;
// Importa el Enum EstadoReembolso

import io.swagger.v3.oas.annotations.media.Schema;
// Permite documentar la clase y atributos en Swagger

import lombok.AllArgsConstructor;
// Genera un constructor con todos los atributos

import lombok.Data;
// Genera getters, setters, toString, equals y hashCode

import lombok.NoArgsConstructor;
// Genera un constructor vacío

import java.time.LocalDate;
// Permite trabajar con fechas

@Data
// Lombok genera automáticamente getters y setters

@NoArgsConstructor
// Genera un constructor vacío: new CancelacionResponse()

@AllArgsConstructor
// Genera un constructor con todos los atributos

@Schema(description = "Respuesta con la información de una cancelación")
// Descripción general que aparece en Swagger
public class CancelacionResponse {

    @Schema(
            description = "ID de la cancelación",
            example = "1"
    )
    // Muestra en Swagger el ID y un ejemplo

    private Long idCancelacion;
    // Guarda el identificador único de la cancelación

    @Schema(
            description = "Motivo de la cancelación",
            example = "Cambio de planes del cliente"
    )
    // Documenta el motivo en Swagger

    private String motivo;
    // Guarda el motivo de la cancelación

    @Schema(
            description = "Fecha de la cancelación",
            example = "2026-06-10"
    )
    // Documenta la fecha en Swagger

    private LocalDate fechaCancelacion;
    // Guarda la fecha de la cancelación

    @Schema(
            description = "Estado del reembolso asociado a la cancelación",
            example = "PENDIENTE"
    )
    // Documenta el estado del reembolso

    private EstadoReembolso estadoReembolso;
    // Guarda el estado del reembolso

    @Schema(
            description = "ID de la reserva asociada a la cancelación",
            example = "123"
    )
    // Documenta el ID de la reserva

    private Long idReserva;
    // Guarda el ID de la reserva relacionada
}