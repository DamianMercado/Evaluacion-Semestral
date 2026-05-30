package com.ReservaPro.ms_promocion.dto.response;

import lombok.Data;

@Data
public class PromocionResponse {
    private Long idPromocion;
    private String codigoPromocion;
    private String descripcion;
    private Double porcentajeDescuento;
    private Boolean activaPromocion;
}
