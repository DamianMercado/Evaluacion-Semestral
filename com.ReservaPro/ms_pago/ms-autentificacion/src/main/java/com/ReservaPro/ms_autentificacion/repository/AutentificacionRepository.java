package com.ReservaPro.ms_autentificacion.repository;
import com.ReservaPro.ms_autentificacion.model.Autentificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutentificacionRepository extends JpaRepository<Autentificacion, Long> {

}
