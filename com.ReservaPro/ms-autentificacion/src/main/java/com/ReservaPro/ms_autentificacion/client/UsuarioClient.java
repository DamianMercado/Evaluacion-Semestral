package com.ReservaPro.ms_autentificacion.client;

import com.ReservaPro.ms_autentificacion.dto.response.UsuarioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "ms-usuario",
        url = "http://localhost:8084"  // Puerto de ms-usuario
)
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioResponse obtenerUsuarioPorId(@PathVariable("id") Long id);
}