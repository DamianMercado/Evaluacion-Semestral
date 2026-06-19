package com.ReservaPro.ms_usuario.dto.request;

import com.ReservaPro.ms_usuario.enums.RolUsuario;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Schema(description = "Datos necesarios para crear o actualizar un usuario")
public class UsuarioRequest {

    @Schema(
            description = "Nombre del usuario",
            example = "Juan"
    )
    @NotBlank(message = "El nombre es obligatorio")
    @Size(
            min = 1,
            max = 100,
            message = "El nombre debe tener un largo entre 1 y 100"
    )
    private String nombre;

    @Schema(
            description = "Apellido del usuario",
            example = "Pérez"
    )
    @NotBlank(message = "El apellido es obligatorio")
    @Size(
            min = 1,
            max = 100,
            message = "El apellido debe tener un largo entre 1 y 100"
    )
    private String apellido;

    @Schema(
            description = "Contraseña del usuario",
            example = "123456"
    )
    @NotBlank(message = "El password es obligatorio")
    @Size(
            min = 1,
            max = 50,
            message = "El password debe tener un largo entre 1 y 50"
    )
    private String password;

    @Schema(
            description = "Correo electrónico del usuario",
            example = "juan@gmail.com"
    )
    @NotBlank(message = "El email es obligatorio")
    @Size(
            min = 1,
            max = 50,
            message = "El email debe tener un largo entre 1 y 50"
    )
    private String email;

    @Schema(
            description = "RUT del usuario",
            example = "12345678-9"
    )
    @NotBlank(message = "El rut es obligatorio")
    @Size(
            min = 1,
            max = 12,
            message = "El rut debe tener un largo entre 1 y 12"
    )
    private String rut;

    @Schema(
            description = "Rol del usuario",
            example = "CLIENTE"
    )
    @NotNull(message = "El rol es obligatorio")
    private RolUsuario rol;
}