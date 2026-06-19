package com.ReservaPro.ms_notificacion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "ms-cancelacion",
        url = "http://localhost:8082"
)
public interface CancelacionClient {

    @GetMapping("/api/v1/cancelaciones/{id}")
    Object obtenerCancelacionPorId(@PathVariable Long id);
}
