package com.ReservaPro.ms_promocion.repository;

import com.ReservaPro.ms_promocion.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    Optional<Promocion> findByCodigoPromocion(String codigoPromocion);
}
