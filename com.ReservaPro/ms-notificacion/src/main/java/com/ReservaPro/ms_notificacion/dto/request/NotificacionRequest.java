package com.ReservaPro.ms_notificacion.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Schema(description = "Datos necesarios para crear o actualizar una notificación")
public class NotificacionRequest {

    @Schema(
            description = "ID del usuario que recibirá la notificación",
            example = "10"
    )
    @NotNull(message = "El idUsuario es obligatorio")
    private Long idUsuario;

    @Schema(
            description = "ID de la reserva asociada",
            example = "5"
    )
    private Long idReserva;

    @Schema(
            description = "ID de la cancelación asociada",
            example = "2"
    )
    private Long idCancelacion;

    @Schema(
            description = "Mensaje de la notificación",
            example = "Reserva confirmada correctamente"
    )
    @NotBlank(message = "El mensaje es obligatorio")
    @Size(
            min = 1,
            max = 200,
            message = "El mensaje debe tener entre 1 y 200 caracteres"
    )
    private String mensaje;

    @Schema(
            description = "Tipo de notificación",
            example = "EMAIL"
    )
    @NotBlank(message = "El tipo es obligatorio")
    @Size(
            min = 1,
            max = 20,
            message = "El tipo debe tener entre 1 y 20 caracteres"
    )
    private String tipo;

    @Schema(
            description = "Indica si la notificación fue leída",
            example = "false"
    )
    @NotNull(message = "El campo leida es obligatorio")
    private Boolean leida;
}