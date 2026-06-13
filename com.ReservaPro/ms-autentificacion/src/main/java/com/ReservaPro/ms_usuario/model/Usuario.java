package com.ReservaPro.ms_usuario.model;

import com.ReservaPro.ms_usuario.enums.RolUsuario;
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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "Apellido", length = 100, nullable = false)
    private String Apellido;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "rut", length = 12, nullable = false)
    private String rut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RolUsuario rol;


}


