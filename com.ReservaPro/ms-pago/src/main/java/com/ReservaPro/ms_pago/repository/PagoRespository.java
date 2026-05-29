package com.ReservaPro.ms_pago.repository;

import com.ReservaPro.ms_pago.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRespository extends JpaRepository<Pago, Long> {
}
