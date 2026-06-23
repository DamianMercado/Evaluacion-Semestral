package com.ReservaPro.ms_calificacion.repository;

import com.ReservaPro.ms_calificacion.enums.EstadoCalificacion;
import com.ReservaPro.ms_calificacion.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByIdUsuario(Long idUsuario);
    List<Calificacion> findByIdReserva(Long idReserva);
    List<Calificacion> findByEstado(EstadoCalificacion estado);
    boolean existsByIdReservaAndIdUsuario(Long idReserva, Long idUsuario);
}