package com.ReservaPro.ms_reserva.model;

import com.ReservaPro.ms_reserva.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long id;

    // IDs de otros microservicios
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_gestion_servicio", nullable = false)
    private Long idGestionServicio;

    @Column(name = "id_promocion")
    private Long idPromocion;

    @Column(name = "id_calificacion")
    private Long idCalificacion;

    @Column(name = "id_pago")
    private Long idPago;

    // Campos propios
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    @Column(name = "precio_reserva", nullable = false, columnDefinition = "DECIMAL")
    private Double precioReserva;

    @Column(name = "descuento_aplicado", columnDefinition = "DECIMAL")
    private Double descuentoAplicado;

    @Column(name = "precio_final", nullable = false, columnDefinition = "DECIMAL")
    private Double precioFinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_reserva", nullable = false, length = 30)
    private EstadoReserva estadoReserva;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (estadoReserva == null) {
            estadoReserva = EstadoReserva.PENDIENTE_PAGO;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}