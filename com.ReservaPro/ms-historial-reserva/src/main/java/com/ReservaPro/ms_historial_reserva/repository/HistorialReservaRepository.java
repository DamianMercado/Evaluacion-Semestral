package com.ReservaPro.ms_historial_reserva.repository;

import com.ReservaPro.ms_historial_reserva.model.HistorialReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialReservaRepository
        extends JpaRepository<HistorialReserva, Long> {

    List<HistorialReserva> findByIdReserva(Long idReserva);
}