package com.ReservaPro.ms_disponibilidad.repository;

import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DisponibilidadRepository
        extends JpaRepository<Disponibilidad, Long> {

    List<Disponibilidad> findByFecha(LocalDate fecha);

    List<Disponibilidad> findByActivo(Boolean activo);

    List<Disponibilidad> findByEstado(
            Disponibilidad.EstadoDisponibilidad estado
    );
}
