package com.ReservaPro.ms_usuario.repository;

import com.ReservaPro.ms_usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}