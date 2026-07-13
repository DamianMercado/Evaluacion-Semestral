package com.ReservaPro.ms_gestion_servicio.model;

import com.ReservaPro.ms_gestion_servicio.enums.EstadoServicio;
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
@Table(name = "gestion_servicio")
public class GestionServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gestion_servicio")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "precio_servicio", columnDefinition = "DECIMAL", nullable = false)
    private Double precioServicio;

    @Column(name = "duracion_minuto", nullable = false)
    private Integer duracionMinuto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_servicio", nullable = false, length = 30)
    private EstadoServicio estadoServicio;

    @Column(name = "ubicacion", nullable = false, length = 200)
    private String ubicacion;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "condiciones", nullable = false, length = 500)
    private String condiciones;

    @Column(name = "proveedor_id", nullable = false)
    private Long proveedorId;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (estadoServicio == null) {
            estadoServicio = EstadoServicio.ACTIVADO;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}