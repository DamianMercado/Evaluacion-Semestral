package com.ReservaPro.ms_promocion.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPromocion;

    @Column (name = "codigo", nullable = false)
    private String codigoPromocion;

    @Column (name= "descripcion", nullable = false)
    private String descripcion;

    @Column (name = "porcentaje_descuento")
    private Double porcentajeDescuento;

    @Column ( name = "activa_promocion")
    private Boolean activaPromocion;
}
