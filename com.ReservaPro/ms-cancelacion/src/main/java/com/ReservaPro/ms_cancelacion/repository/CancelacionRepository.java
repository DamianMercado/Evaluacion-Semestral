package com.ReservaPro.ms_cancelacion.repository;

import com.ReservaPro.ms_cancelacion.model.Cancelacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancelacionRepository extends JpaRepository<Cancelacion, Long> {
}