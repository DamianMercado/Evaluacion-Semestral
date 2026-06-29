package com.ReservaPro.ms_notificacion.repository;

import com.ReservaPro.ms_notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository
        extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByIdUsuario(Long idUsuario);

    List<Notificacion> findByIdReserva(Long idReserva);

    List<Notificacion> findByLeida(Boolean leida);
}