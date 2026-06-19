package com.ReservaPro.ms_reserva.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "ms-usuario",
        url = "http://localhost:8081"
)
public interface UsuarioClient {

    @GetMapping("/api/v1/usuario/{id}")
    Object obtenerUsuarioPorId(
            @PathVariable("id") Long id
    );
}