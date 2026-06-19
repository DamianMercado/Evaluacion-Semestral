package com.ReservaPro.ms_usuario.dto.response;

import com.ReservaPro.ms_usuario.enums.RolUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Respuesta con la información de un usuario")
public class UsuarioResponse {

    @Schema(description = "ID del usuario", example = "1")
    private Long idUsuario;

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;

    @Schema(description = "Correo electrónico del usuario", example = "juan@gmail.com")
    private String email;

    @Schema(description = "RUT del usuario", example = "12345678-9")
    private String rut;

    @Schema(description = "Rol del usuario", example = "CLIENTE")
    private RolUsuario rol;
}