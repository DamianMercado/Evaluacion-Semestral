package com.ReservaPro.ms_pago.dto.response;

import lombok.Data;

@Data
public class CalcularDescuentoResponse {
    private String codigoPromocion;
    private Double montoOriginal;
    private Double descuentoAplicado;
    private Double montoFinal;
}
