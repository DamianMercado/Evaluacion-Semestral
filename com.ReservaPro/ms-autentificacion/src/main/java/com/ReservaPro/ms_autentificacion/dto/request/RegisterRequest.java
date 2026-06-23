package com.ReservaPro.ms_autentificacion.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 1, max = 100, message = "El apellido debe tener entre 1 y 100 caracteres")
    private String apellido;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "El RUT es obligatorio")
    @Size(min = 1, max = 12, message = "El RUT debe tener entre 1 y 12 caracteres")
    private String rut;

    private String rol = "CLIENTE"; // ADMINISTRADOR, CLIENTE, OPERADOR_SERVICIO
}
