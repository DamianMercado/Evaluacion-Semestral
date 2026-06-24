package com.ReservaPro.ms_gestion_servicio.repository;

import com.ReservaPro.ms_gestion_servicio.model.GestionServicio;
import com.ReservaPro.ms_gestion_servicio.enums.EstadoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GestionServicioRepository extends JpaRepository<GestionServicio, Long> {
    List<GestionServicio> findByEstadoServicio(EstadoServicio estadoServicio);
    List<GestionServicio> findByNombreContainingIgnoreCase(String nombre);
    List<GestionServicio> findByProveedorId(Long proveedorId);
    boolean existsByNombreIgnoreCase(String nombre);
}