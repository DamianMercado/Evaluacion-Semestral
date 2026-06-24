package com.ReservaPro.ms_gestion_servicio.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuario")
public interface UsuarioOperadorClient {

    @GetMapping("/api/v1/usuarios/{id}/validar-operador")
    Boolean esOperadorServicio(@PathVariable("id") Long id);
}
