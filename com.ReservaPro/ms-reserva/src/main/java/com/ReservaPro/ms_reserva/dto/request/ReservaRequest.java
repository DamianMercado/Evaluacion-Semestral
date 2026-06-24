package com.ReservaPro.ms_reserva.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Datos para crear o actualizar una reserva")
public class ReservaRequest {

    @Schema(description = "ID del usuario", example = "1")
    @NotNull(message = "El idUsuario es obligatorio")
    @Min(value = 1, message = "El idUsuario debe ser mayor o igual a 1")
    private Long idUsuario;

    @Schema(description = "ID del servicio", example = "1")
    @NotNull(message = "El idGestionServicio es obligatorio")
    @Min(value = 1, message = "El idGestionServicio debe ser mayor o igual a 1")
    private Long idGestionServicio;

    @Schema(description = "ID de la promoción (puede ser null)", example = "1")
    @Min(value = 0, message = "El idPromocion debe ser mayor o igual a 0")
    private Long idPromocion;

    @Schema(description = "ID de la calificación", example = "1")
    @Min(value = 0, message = "El idCalificacion debe ser mayor o igual a 0")
    private Long idCalificacion;

    @Schema(description = "ID del pago (se asigna después de pagar)", example = "1")
    @Min(value = 0, message = "El idPago debe ser mayor o igual a 0")
    private Long idPago;

    @Schema(description = "Fecha de la reserva", example = "2026-06-24T10:00:00")
    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDateTime fechaReserva;

    @Schema(description = "Precio original de la reserva", example = "1000")
    @NotNull(message = "El precioReserva es obligatorio")
    @Positive(message = "El precioReserva debe ser mayor a 0")
    private Double precioReserva;

    @Schema(description = "Descuento aplicado (puede ser 0)", example = "0")
    @Min(value = 0, message = "El descuentoAplicado debe ser mayor o igual a 0")
    private Double descuentoAplicado;

    @Schema(description = "Precio final después del descuento", example = "1000")
    @NotNull(message = "El precioFinal es obligatorio")
    @Positive(message = "El precioFinal debe ser mayor a 0")
    private Double precioFinal;
}