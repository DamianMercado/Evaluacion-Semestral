package com.ReservaPro.ms_promocion.dto.response;

import lombok.Data;

import javax.print.DocFlavor;

@Data
public class CalcularDescuentoResponse {
    private String codigoPromocion;
    private Double montoOriginal;
    private Double descuentoAplicado;
    private Double montoFinal;
}
