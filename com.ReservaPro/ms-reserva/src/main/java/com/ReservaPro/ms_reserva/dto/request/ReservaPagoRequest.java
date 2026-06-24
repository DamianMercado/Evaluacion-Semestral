package com.ReservaPro.ms_reserva.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos para confirmar el pago de una reserva")
public class ReservaPagoRequest {

    @Schema(description = "ID del pago", example = "1")
    @NotNull(message = "El idPago es obligatorio")
    @Min(value = 1, message = "El idPago debe ser mayor o igual a 1")
    private Long idPago;
}