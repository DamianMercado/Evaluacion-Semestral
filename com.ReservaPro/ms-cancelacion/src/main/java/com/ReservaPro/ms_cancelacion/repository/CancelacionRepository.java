package com.ReservaPro.ms_cancelacion.repository;

import com.ReservaPro.ms_cancelacion.model.Cancelacion;
import com.ReservaPro.ms_cancelacion.model.EstadoReembolso;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CancelacionRepository
        extends JpaRepository<Cancelacion, Long> {

    List<Cancelacion> findByIdReserva(Long idReserva);

    List<Cancelacion> findByEstadoReembolso(
            EstadoReembolso estadoReembolso
    );

    List<Cancelacion> findByFechaCancelacion(
            LocalDate fechaCancelacion
    );
}