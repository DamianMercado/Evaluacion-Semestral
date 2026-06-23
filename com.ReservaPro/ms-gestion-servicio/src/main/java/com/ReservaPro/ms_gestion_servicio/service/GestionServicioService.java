package com.ReservaPro.ms_gestion_servicio.service;

import com.ReservaPro.ms_gestion_servicio.client.UsuarioOperadorClient;
import com.ReservaPro.ms_gestion_servicio.model.GestionServicio;
import com.ReservaPro.ms_gestion_servicio.repository.GestionServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class GestionServicioService {

    private GestionServicioRepository gestionServicioRepository;
    private UsuarioOperadorClient usuarioOperador;

//POST SERVICIO VALIDANDO USUARIOOPERADOR

    public boolean guardarServicio(GestionServicio gestionServicio) {
        // Validación inter-servicio usando Feign
        Boolean esProveedorValido = usuarioOperador.esOperadorServicio(gestionServicio.getProveedorId());

        if (Boolean.FALSE.equals(esProveedorValido)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El proveedor especificado no existe o no cuenta con el rol OPERADOR_SERVICIO.");
        }
        return GestionServicioRepository.save(GestionServicioResponse);
    }

//GET SERVICIO POR ID
    public GestionServicio obtenerPorId(Long id) {
        return gestionServicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado"));
    }
//GET SERVICIO
}