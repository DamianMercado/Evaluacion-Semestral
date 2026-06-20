package com.ReservaPro.ms_usuario.model;

import com.ReservaPro.ms_usuario.enums.RolUsuario;
import io.swagger.v3.oas.annotations.media.Schema;

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
@Table(name = "usuario")
@Schema(description = "Entidad que representa a un usuario del sistema")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    @Schema(description = "ID del usuario", example = "1")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;

    @Column(name = "password", nullable = false, length = 100)
    @Schema(description = "Contraseña del usuario", example = "123456")
    private String password;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    @Schema(description = "Correo electrónico del usuario", example = "juan@gmail.com")
    private String email;

    @Column(name = "rut", nullable = false, unique = true, length = 12)
    @Schema(description = "RUT del usuario", example = "12345678-9")
    private String rut;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, length = 30)
    @Schema(description = "Rol del usuario", example = "CLIENTE")
    private RolUsuario rol;
}