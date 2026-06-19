package com.ReservaPro.ms_gestion_servicio.repository;

import com.ReservaPro.ms_gestion_servicio.model.GestionServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GestionServicioRepository extends JpaRepository <GestionServicio, Long>{
};
