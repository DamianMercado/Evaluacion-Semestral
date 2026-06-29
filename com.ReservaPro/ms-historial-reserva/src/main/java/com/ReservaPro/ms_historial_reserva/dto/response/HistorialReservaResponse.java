package com.ReservaPro.ms_historial_reserva.dto.response; // Define el paquete donde se encuentra el DTO

import com.ReservaPro.ms_historial_reserva.model.EstadoReserva; // Importa el Enum EstadoReserva

import io.swagger.v3.oas.annotations.media.Schema; // Importa Schema para Swagger

import lombok.Data; // Genera getters, setters, toString, etc.

import java.time.LocalDateTime; // Permite manejar fecha y hora

@Data // Lombok genera automáticamente getters y setters
@Schema(description = "Respuesta con la información del historial de reserva")
public class HistorialReservaResponse {

    @Schema(description = "ID del historial", example = "1")
    private Long idHistorial; // Identificador único del historial

    @Schema(description = "ID de la reserva asociada", example = "10")
    private Long idReserva; // ID de la reserva

    @Schema(
            description = "Estado anterior de la reserva",
            example = "PENDIENTE"
    )
    private EstadoReserva estadoAnterior; // Estado antes del cambio

    @Schema(
            description = "Nuevo estado de la reserva",
            example = "CONFIRMADA"
    )
    private EstadoReserva estadoNuevo; // Estado después del cambio

    @Schema(
            description = "Fecha y hora en que se realizó el cambio",
            example = "2026-06-28T15:30:00"
    )
    private LocalDateTime fechaCambio; // Fecha del cambio

    @Schema(
            description = "Observación del cambio realizado",
            example = "La reserva fue confirmada por el administrador"
    )
    private String observacion; // Comentario u observación
}