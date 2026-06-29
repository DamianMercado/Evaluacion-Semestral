package com.ReservaPro.ms_reserva.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Respuesta con la información de una reserva")
public class ReservaResponse {

    @Schema(
            description = "ID de la reserva",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "ID del usuario que realizó la reserva",
            example = "10"
    )
    private Long idUsuario;

    @Schema(
            description = "ID del servicio asociado a la reserva",
            example = "5"
    )
    private Long idGestionServicio;

    @Schema(
            description = "ID de la promoción aplicada",
            example = "2"
    )
    private Long idPromocion;

    @Schema(
            description = "ID de la calificación asociada",
            example = "3"
    )
    private Long idCalificacion;

    @Schema(
            description = "ID del pago asociado",
            example = "7"
    )
    private Long idPago;

    @Schema(
            description = "Fecha y hora de la reserva",
            example = "2026-06-29T10:30:00"
    )
    private LocalDateTime fechaReserva;

    @Schema(
            description = "Precio original de la reserva",
            example = "50000.0"
    )
    private Double precioReserva;

    @Schema(
            description = "Descuento aplicado a la reserva",
            example = "5000.0"
    )
    private Double descuentoAplicado;

    @Schema(
            description = "Precio final a pagar",
            example = "45000.0"
    )
    private Double precioFinal;

    @Schema(
            description = "Estado actual de la reserva",
            example = "PENDIENTE_PAGO"
    )
    private String estadoReserva;

    @Schema(
            description = "Fecha de creación del registro",
            example = "2026-06-29T10:00:00"
    )
    private LocalDateTime fechaCreacion;

    @Schema(
            description = "Fecha de la última actualización",
            example = "2026-06-29T10:15:00"
    )
    private LocalDateTime fechaActualizacion;
}