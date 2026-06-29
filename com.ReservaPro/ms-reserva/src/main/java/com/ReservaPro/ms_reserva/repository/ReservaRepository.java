package com.ReservaPro.ms_reserva.repository; // Paquete donde se encuentra el repositorio

import com.ReservaPro.ms_reserva.enums.EstadoReserva; // Importa el enum EstadoReserva
import com.ReservaPro.ms_reserva.model.Reserva; // Importa la entidad Reserva

import org.springframework.data.jpa.repository.JpaRepository; // Proporciona métodos CRUD automáticamente
import org.springframework.stereotype.Repository; // Marca la interfaz como repositorio de Spring

import java.util.List; // Permite trabajar con listas

@Repository // Indica que esta interfaz accede a la base de datos
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // JpaRepository<Entidad, TipoId>
    // Entidad: Reserva
    // TipoId: Long

    List<Reserva> findByIdUsuario(Long idUsuario);
    // Busca todas las reservas asociadas a un usuario específico

    List<Reserva> findByEstadoReserva(EstadoReserva estado);
    // Busca todas las reservas según su estado
    // Ejemplo: PENDIENTE, CONFIRMADA, CANCELADA

    List<Reserva> findByIdUsuarioAndEstadoReserva(
            Long idUsuario,
            EstadoReserva estado
    );
    // Busca reservas de un usuario filtradas además por estado
    // Ejemplo: todas las reservas CONFIRMADAS del usuario 1
}