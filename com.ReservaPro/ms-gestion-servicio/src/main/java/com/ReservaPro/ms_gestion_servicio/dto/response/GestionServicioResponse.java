package com.ReservaPro.ms_gestion_servicio.dto.response;

import lombok.Data;

@Data
public class GestionServicioResponse {

    private Long idServicio;
    private String nombre;
    private Double precioBase;
    private Integer duracionMinuto;
    private String estado;
    private String ubicacion;
    private Integer capacidad;
    private String condiciones;
    private Long proveedorId;

}
