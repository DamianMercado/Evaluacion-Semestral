package com.ReservaPro.ms_promocion.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PromocionRequest {

    private String codigoPromocion;

    @NotBlank (message = "La descricpion no puede estar vacía")
    private String descripcion;

    @NotNull (message = "El porcentaje de descuento no puede estar vacio")
    @DecimalMin(value="0.0", message ="El porcentaje de descuento tiene que ser mayor o igual 0")
    @DecimalMax(value="100.0", message ="El porcentaje de descuento tiene que ser menor o igual a 100%")
    private Double porcentajeDescuento;

    @NotNull(message = "El estado de la promocion es obligatotio")
    private Boolean activaPromocion;
}
