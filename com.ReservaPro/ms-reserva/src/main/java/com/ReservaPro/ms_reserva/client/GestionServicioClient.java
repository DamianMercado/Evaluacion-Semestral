package com.ReservaPro.ms_reserva.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-gestion-servicio")
public interface GestionServicioClient {

    @GetMapping("/api/v1/gestion-servicio/{id}")
    Object obtenerServicioPorId(@PathVariable("id") Long id);
}