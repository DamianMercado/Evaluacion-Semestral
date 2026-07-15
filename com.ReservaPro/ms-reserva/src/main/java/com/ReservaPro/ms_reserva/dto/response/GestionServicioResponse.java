package com.ReservaPro.ms_reserva.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

//Copia local
@Data
public class GestionServicioResponse {

    private Long id;
    private String nombre;
    private Double precioServicio;
    private Integer duracionMinuto;
    private String estadoServicio;
    private String ubicacion;
    private Integer capacidad;
    private String condiciones;
    private Long proveedorId;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}