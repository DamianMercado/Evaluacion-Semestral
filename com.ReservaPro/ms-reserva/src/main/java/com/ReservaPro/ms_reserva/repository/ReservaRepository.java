package com.ReservaPro.ms_reserva.repository;

import com.ReservaPro.ms_reserva.enums.EstadoReserva;
import com.ReservaPro.ms_reserva.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByIdUsuario(Long idUsuario);

    List<Reserva> findByEstadoReserva(EstadoReserva estado);

    List<Reserva> findByIdUsuarioAndEstadoReserva(Long idUsuario, EstadoReserva estado);
}