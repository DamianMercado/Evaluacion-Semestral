package com.ReservaPro.ms_reserva.repository;
import com.ReservaPro.ms_reserva.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

}
