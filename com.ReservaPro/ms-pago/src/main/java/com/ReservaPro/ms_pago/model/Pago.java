package com.ReservaPro.ms_pago.model;

import com.ReservaPro.ms_pago.enums.Estado;
import com.ReservaPro.ms_pago.enums.TipoBanco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long id;

    @Column(name = "id_reserva", nullable = false)
    private Long idReserva;

    @Column(name = "monto_original", nullable = false)
    private Double montoOriginal;

    @Column(name = "monto", nullable = false)
    private Double montoPago;

    @Column(name = "metodo", nullable = false, length = 100)
    private String metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "banco", nullable = false, length = 100)
    private TipoBanco tipoBanco;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estadoPago;

    @Column(name = "fecha", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "aplica_descuento", nullable = false)
    private boolean aplicaDescuento;

    @Column(name = "codigo_promocion", length = 50)
    private String codigoPromocion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (estadoPago == null) {
            estadoPago = Estado.PENDIENTE;
        }
        if (fechaPago == null) {
            fechaPago = LocalDate.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}