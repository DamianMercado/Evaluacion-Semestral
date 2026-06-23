package com.ReservaPro.ms_calificacion.repository;

import com.ReservaPro.ms_calificacion.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByRut(String rut);
    boolean existsByEmail(String email);
    boolean existsByRut(String rut);
}