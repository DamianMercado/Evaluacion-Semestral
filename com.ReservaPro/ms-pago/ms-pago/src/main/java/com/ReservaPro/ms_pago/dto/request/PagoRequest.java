package com.ReservaPro.ms_pago.dto.request;

import com.ReservaPro.ms_pago.model.TipoBanco;
import com.ReservaPro.ms_pago.model.Estado;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PagoRequest {

    @NotNull(message = "El monto de pago es obligatorio")
    @Positive(message = "El monto tiene que ser mayor a 0")
    private Double montoPago;

    @NotNull(message = "El metodo de pago es obligatorio")
    private String metodoPago;

    @NotNull(message = "El tipo de banco es obligatorio")
    private TipoBanco tipoBanco;

    @NotNull(message = "El estado es obligatorio")
    private Estado estadoPago;

    private LocalDate fechaPago;

    @NotNull (message = "Escribe cualquier cosa para probar")
    private String prueba;

    @PositiveOrZero
    private Double descuento;

    private String codigoPromocion;

}
