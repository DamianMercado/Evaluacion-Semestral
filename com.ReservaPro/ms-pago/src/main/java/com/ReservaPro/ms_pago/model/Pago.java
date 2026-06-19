package com.ReservaPro.ms_pago.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    @Column (name= "monto_original", nullable = false)
    private Double montoOriginal;

    @Column (name= "monto", nullable = false)
    private Double montoPago;

    @Column (name = "metodo", nullable = false, length = 100)
    private String metodoPago;

    @Enumerated(EnumType.STRING)
    @Column (name = "banco", nullable = false, length = 100)
    private TipoBanco tipoBanco;

    @Enumerated(EnumType.STRING)
    @Column (name = "estado",  nullable = false)
    private Estado estadoPago;

    @Column (name = "fecha", nullable = false)
    private LocalDate fechaPago;

    @Column (name = "aplica_descuento", nullable = false)
    private boolean aplicaDescuento;

    @Column (name ="codigo_promocion", length = 50)
    private String codigoPromocion;
}
