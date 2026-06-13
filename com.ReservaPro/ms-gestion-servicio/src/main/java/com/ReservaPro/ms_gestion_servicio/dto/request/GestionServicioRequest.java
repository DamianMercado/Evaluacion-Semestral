package com.ReservaPro.ms_gestion_servicio.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GestionServicioRequest {

    @NotNull(message = "El idServicio es obligatorio")
    private Long idServicio;
    @NotNull(message = "El nombre es obligatorio")
    private String nombre;
    @NotNull(message = "El precioBase es obligatorio")
    private Double precioBase;
    @NotNull(message = "El duracionMinuto es obligatorio")
    private Integer duracionMinuto;
    @NotNull(message = "El estado es obligatorio")
    private String estado;
    @NotNull(message = "El ubicacion es obligatorio")
    private String ubicacion;
    @NotNull(message = "El capacidad es obligatorio")
    private Integer capacidad;
    @NotNull(message = "El condiciones es obligatorio")
    private String condiciones;
    @NotNull(message = "El proveedorId es obligatorio")
    private Long proveedorId;

}
