package com.ReservaPro.ms_pago.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para notificar a ms-reserva sobre el pago")
public class ReservaPagoRequest {

    @Schema(description = "ID del pago", example = "1")
    @NotNull(message = "El idPago es obligatorio")
    @Min(value = 1, message = "El idPago debe ser mayor o igual a 1")
    private Long id;
}