package com.ReservaPro.ms_promocion.dto.response;

import lombok.Data;

@Data
public class PromocionResponse {

    private Long id;
    private String codigoPromocion;
    private String descripcion;
    private Double porcentajeDescuento;
    private Boolean activaPromocion;
}
