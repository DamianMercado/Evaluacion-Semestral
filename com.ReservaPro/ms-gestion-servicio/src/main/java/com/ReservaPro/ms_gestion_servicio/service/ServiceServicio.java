package com.ReservaPro.ms_gestion_servicio.service;

import com.ReservaPro.ms_gestion_servicio.client.UsuarioClient;
import com.ReservaPro.ms_gestion_servicio.model.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public Servicio guardarServicio(Servicio servicio) {
        // Validación inter-servicio usando Feign
        Boolean esProveedorValido = usuarioClient.esOperadorServicio(servicio.getProveedorId());

        if (Boolean.FALSE.equals(esProveedorValido)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El proveedor especificado no existe o no cuenta con el rol OPERADOR_SERVICIO.");
        }
        return servicioRepository.save(servicio);
    }

    public Servicio obtenerPorId(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado"));
    }
}
