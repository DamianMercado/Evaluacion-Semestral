package com.ReservaPro.ms_autentificacion.dto.response;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String rut;
    private String rol;  // ADMINISTRADOR, CLIENTE, OPERADOR_SERVICIO
}