package com.ReservaPro.ms_reserva.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "ms-calificacion",
        url = "http://localhost:8081"
)
public interface CalificacionClient {

    @GetMapping("/api/v1/calificacion/{id}")
    Object obtenerCalificacionPorId(
            @PathVariable("id") Long id
    );
}