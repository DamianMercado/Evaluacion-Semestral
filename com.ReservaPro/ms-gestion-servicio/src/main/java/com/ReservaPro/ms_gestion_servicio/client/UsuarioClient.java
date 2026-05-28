package com.ReservaPro.ms_gestion_servicio.client;

public interface UsuarioClient {
    @FeignClient(name = "ms-usuario")
    public interface UsuarioClient {
        @GetMapping("/usuarios/{id}/validar-operador")
        Boolean esOperadorServicio(@PathVariable("id") Long id);
    }
}
