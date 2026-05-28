package com.ReservaPro.ms_reserva.model;

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
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con ms-usuario
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    // CORRECCIÓN: Relación con ms-servicio.
    @Column(name = "servicio_id", nullable = false)
    private Long servicioId;

    // Fechas importantes
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva; // La fecha en la que el cliente usará el servicio

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    // Estado (Pendiente - Confirmada - Cancelada - Completada)
    @Column(name = "estado", length = 20, nullable = false)
    private String estado;

    // Precio original del servicio
    @Column(name = "total", nullable = false)
    private Double total;

    // dato de ms-promocion
    @Column(name = "total_promocion")
    private Double totalPromocion;

    // Opcional: Guardar el ID de la promoción aplicada para auditoría de ms-promocion
    @Column(name = "promocion_id")
    private Long promocionId;

    // Ciclo de vida de JPA para setear la fecha de creación automáticamente
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }
}