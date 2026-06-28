package com.ReservaPro.ms_notificacion.repository;

import com.ReservaPro.ms_notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
} //acede a la base datos y elimina registro sin hacer consultas