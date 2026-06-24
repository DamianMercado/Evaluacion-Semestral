package com.ReservaPro.ms_reserva.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-calificacion")
public interface CalificacionClient {

    @GetMapping("/api/v1/calificaciones/{id}")
    Object obtenerCalificacionPorId(@PathVariable("id") Long id);
}