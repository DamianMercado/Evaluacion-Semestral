package com.ReservaPro.ms_disponibilidad.repository;

import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;
import com.ReservaPro.ms_disponibilidad.model.EstadoDisponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long> {

    List<Disponibilidad> findByFecha(LocalDate fecha);

    List<Disponibilidad> findByActivo(Boolean activo);

    List<Disponibilidad> findByEstado(EstadoDisponibilidad estado);
}