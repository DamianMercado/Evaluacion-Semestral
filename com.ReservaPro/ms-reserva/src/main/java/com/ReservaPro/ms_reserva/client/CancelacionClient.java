package com.ReservaPro.ms_reserva.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
        name = "ms-cancelacion",
        url = "http://localhost:8081"
)
public interface CancelacionClient {

    @GetMapping("/api/v1/cancelacion/{id}")
    Object obtenerCancelacionPorId(
            @PathVariable("id") Long id
    );
}
