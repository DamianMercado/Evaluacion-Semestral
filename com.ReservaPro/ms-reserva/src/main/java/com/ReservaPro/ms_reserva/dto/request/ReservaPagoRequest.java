package com.ReservaPro.ms_reserva.dto.request;

// Permite documentar la clase y atributos en Swagger
import io.swagger.v3.oas.annotations.media.Schema;

// Validaciones
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// Genera automáticamente getters, setters, toString, etc.
import lombok.Data;

@Data
@Schema(
        description = "Datos necesarios para confirmar el pago de una reserva"
)
public class ReservaPagoRequest {

    @Schema(
            description = "ID del pago asociado a la reserva",
            example = "1"
    )
    @NotNull(
            message = "El idPago es obligatorio"
    )
    // Valida que el valor no sea null

    @Min(
            value = 1,
            message = "El idPago debe ser mayor o igual a 1"
    )
    // Valida que el ID sea mayor o igual a 1

    private Long idPago;
    // Guarda el ID del pago que se asociará a la reserva
}