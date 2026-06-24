package com.ReservaPro.ms_pago.dto.request;

import com.ReservaPro.ms_pago.enums.TipoBanco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "Datos para crear un pago")
public class PagoRequest {

    @Schema(description = "ID de la reserva", example = "1")
    @NotNull(message = "El idReserva es obligatorio")
    @Min(value = 1, message = "El idReserva debe ser mayor o igual a 1")
    private Long idReserva;

    @Schema(description = "Monto original del pago", example = "100.00")
    @NotNull(message = "El montoOriginal es obligatorio")
    @Positive(message = "El montoOriginal debe ser mayor a 0")
    private Double montoOriginal;

    @Schema(description = "Monto final del pago (con descuento aplicado)", example = "90.00")
    @NotNull(message = "El montoPago es obligatorio")
    @Positive(message = "El montoPago debe ser mayor a 0")
    private Double montoPago;

    @Schema(description = "Método de pago", example = "CREDITO")
    @NotBlank(message = "El metodoPago es obligatorio")
    private String metodoPago;

    @Schema(description = "Tipo de banco", example = "SANTANDER")
    @NotNull(message = "El tipoBanco es obligatorio")
    private TipoBanco tipoBanco;

    @Schema(description = "Código de promoción (opcional)", example = "DESCUENTO10")
    private String codigoPromocion;
}