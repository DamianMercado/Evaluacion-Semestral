package com.ReservaPro.ms_cancelacion.dto.request;
// Define el paquete donde se encuentra la clase CancelacionRequest

import com.ReservaPro.ms_cancelacion.model.EstadoReembolso;
// Importa el Enum EstadoReembolso para usar sus valores

import io.swagger.v3.oas.annotations.media.Schema;
// Permite documentar la clase y sus atributos en Swagger

import jakarta.validation.constraints.NotBlank;
// Valida que un texto no sea nulo ni esté vacío

import jakarta.validation.constraints.NotNull;
// Valida que un atributo no sea nulo

import lombok.Data;
// Genera automáticamente getters, setters, toString, equals y hashCode

import java.time.LocalDate;
// Permite trabajar con fechas

@Data
// Lombok genera automáticamente métodos getter y setter

@Schema(description = "Datos necesarios para crear o actualizar una cancelación")
// Descripción general que aparecerá en Swagger
public class CancelacionRequest {

    @Schema(
            description = "Motivo de la cancelación",
            example = "Cambio de planes del cliente"
    )
    // Muestra en Swagger la descripción y ejemplo del campo

    @NotBlank(message = "El motivo es obligatorio")
    // Valida que el motivo no sea nulo ni vacío

    private String motivo;
    // Guarda el motivo de la cancelación

    @Schema(
            description = "Fecha en que se realiza la cancelación",
            example = "2026-06-10"
    )
    // Documenta el campo fecha en Swagger

    @NotNull(message = "La fecha de cancelación es obligatoria")
    // Valida que la fecha exista

    private LocalDate fechaCancelacion;
    // Guarda la fecha en que se realiza la cancelación

    @Schema(
            description = "Estado del reembolso. Valores permitidos: PENDIENTE, REEMBOLSADO",
            example = "PENDIENTE"
    )
    // Documenta el estado del reembolso en Swagger

    @NotNull(message = "El estado del reembolso es obligatorio")
    // Valida que el estado tenga un valor

    private EstadoReembolso estadoReembolso;
    // Guarda el estado del reembolso usando el Enum

    @Schema(
            description = "ID de la reserva asociada a la cancelación",
            example = "123"
    )
    // Documenta el ID de la reserva en Swagger

    @NotNull(message = "El id de la reserva es obligatorio")
    // Valida que se envíe el ID de la reserva

    private Long idReserva;
    // Guarda el identificador de la reserva asociada
}