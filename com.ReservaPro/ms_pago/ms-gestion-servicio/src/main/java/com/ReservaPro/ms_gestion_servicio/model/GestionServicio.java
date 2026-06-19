package com.ReservaPro.ms_gestion_servicio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "servicio")
public class GestionServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "precio_base", nullable = false)
    private Double precioBase;

    @Column(name = "duracion_minuto", nullable = false)
    private Integer duracionMinuto;

    @Column(name = "estado", length = 50, nullable = false)
    private String estado;

    @Column(name = "ubicacion", length = 150, nullable = false)
    private String ubicacion;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "condiciones", length = 500, nullable = false)
    private String condiciones;

    @Column(name = "proveedor_id", nullable = false)
    private Long proveedorId;
}
