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
@Table(name = "servicios") // Buena práctica: nombres de tablas en minúscula
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long idServicio;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    // CORRECCIÓN: Cambiado de precioPromocion a precioBase. El ms-servicio maneja el costo estándar.
    @Column(name = "precio_base", nullable = false)
    private Double precioBase;

    // CORRECCIÓN: Se eliminó el length innecesario para un Integer
    @Column(name = "duracion_minuto", nullable = false)
    private Integer duracionMinuto;

    // Estado ("ACTIVO", "MANTENIMIENTO", "INACTIVO")
    @Column(name = "estado", length = 50, nullable = false)
    private String estado;

    // Direccion
    @Column(name = "ubicacion", length = 150, nullable = false)
    private String ubicacion;

    // Capacidad de personas en el servicio
    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    // Las condiciones suelen ser textos largos
    @Column(name = "condiciones", length = 500, nullable = false)
    private String condiciones;

    // REQUERIMIENTO DEL CASO: El sistema menciona que los "proveedores de servicios deben poder registrar..."
    // Guardamos el ID del proveedor (que vendría de un ms-usuario) para saber a quién pertenece este servicio.
    @Column(name = "proveedor_id", nullable = false)
    private Long proveedorId;
}
