package com.ReservaPro.ms_usuario.dto.request;

import jakarta.persistence.Column;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@Data
public class UsuarioRequest {

    @NotBlank( message = "El nombre es obligatorio")
    @Size(min = 1, max = 100, message = "El Nombre debe tener un largo entre 1 y 100")
    private String nombre;

    @NotBlank( message = "El apellido es obligatorio")
    @Size(min = 1, max = 100, message = "El apellido debe tener un largo entre 1 y 100")
    private String apellido;

    @NotBlank( message = "El password es obligatorio")
    @Size(min = 1, max = 50, message = "El password debe tener un largo entre 1 y 50")
    private String password;

    @NotBlank( message = "El email es obligatorio")
    @Size(min = 1, max = 50, message = "El email debe tener un largo entre 1 y 50")
    private String email;

    @NotBlank( message = "El rut es obligatorio")
    @Size(min = 1, max = 12, message = "El rut debe tener un largo entre 1 y 12")
    private String rut;
}
