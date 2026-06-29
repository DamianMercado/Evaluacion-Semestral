package com.ReservaPro.ms_reserva.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        description = "Respuesta con información completa de una reserva, incluyendo datos de otros microservicios"
)
public class ReservaCompletaResponse {

    @Schema(
            description = "Información principal de la reserva"
    )
    private ReservaResponse reserva;
    // Guarda los datos propios de la reserva

    @Schema(
            description = "Información del usuario asociado a la reserva"
    )
    private Object usuario;
    // Guarda la información obtenida desde ms-usuario

    @Schema(
            description = "Información del servicio reservado"
    )
    private Object servicio;
    // Guarda la información obtenida desde ms-gestion-servicio

    @Schema(
            description = "Información del pago asociado a la reserva"
    )
    private Object pago;
    // Guarda la información obtenida desde ms-pago

    @Schema(
            description = "Información de la calificación asociada a la reserva"
    )
    private Object calificacion;
    // Guarda la información obtenida desde ms-calificacion

    @Schema(
            description = "Información de la cancelación asociada a la reserva"
    )
    private Object cancelacion;
    // Guarda la información obtenida desde ms-cancelacion
}