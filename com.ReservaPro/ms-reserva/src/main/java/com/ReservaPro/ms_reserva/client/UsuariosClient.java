package com.ReservaPro.ms_reserva.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-servicio", url = "localhost:9001/api/v1")
public interface UsuariosClient {

    @GetMapping("/usuarios/{id}")
    Usuario getUsuario(@PathVariable Long id);
}
