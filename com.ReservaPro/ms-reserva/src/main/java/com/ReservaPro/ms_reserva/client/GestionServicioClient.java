package com.ReservaPro.ms_reserva.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
        name = "ms-gestion-servicio",
        url = "http://localhost:8081"
)
public interface GestionServicioClient {

    @GetMapping("/api/v1/gestion-servicio/{id}")
    Object obtenerGestionServicioPorId(
            @PathVariable("id") Long id
    );
}

