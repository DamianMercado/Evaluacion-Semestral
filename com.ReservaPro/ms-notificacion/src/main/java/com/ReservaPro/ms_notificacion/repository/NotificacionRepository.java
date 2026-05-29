package com.ReservaPro.ms_notificacion.repository;

import com.ReservaPro.ms_notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository
        extends JpaRepository<Notificacion, Long> {

}