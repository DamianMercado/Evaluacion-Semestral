package com.ReservaPro.ms_usuario.dto.response;

import com.ReservaPro.ms_usuario.enums.RolUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UsuarioResponse {
    private Long id;

    private String nombre;

    private String Apellido;

    private String password;

    private String email;

    private String rut;

    private RolUsuario rol;
}
