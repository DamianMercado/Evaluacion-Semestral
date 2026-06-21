package com.ReservaPro.ms_disponibilidad.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "ms-reserva",
        url = "http://localhost:8084"
)
public interface ReservaClient {

    @GetMapping("/api/v1/reservas/{id}")
    Object obtenerReservaPorId(@PathVariable Long id);
}