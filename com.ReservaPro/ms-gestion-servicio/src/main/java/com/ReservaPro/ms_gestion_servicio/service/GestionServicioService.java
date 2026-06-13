package com.ReservaPro.ms_gestion_servicio.service;

import com.ReservaPro.ms_gestion_servicio.client.UsuarioOperador;
import com.ReservaPro.ms_gestion_servicio.dto.request.GestionServicioRequest;
import com.ReservaPro.ms_gestion_servicio.dto.response.GestionServicioResponse;
import com.ReservaPro.ms_gestion_servicio.model.GestionServicio;
import com.ReservaPro.ms_gestion_servicio.repository.GestionServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GestionServicioService {

    @Autowired
    private GestionServicioRepository gestionServicioRepository;

    @Autowired
    private UsuarioOperador usuarioOperador;

//POST SERVICIO VALIDANDO USUARIOOPERADOR
    public boolean guardarServicio(GestionServicio gestionServicio) {
        // Validación inter-servicio usando Feign
        Boolean esProveedorValido = usuarioOperador.esOperadorServicio(gestionServicio.getProveedorId());

        if (Boolean.FALSE.equals(esProveedorValido)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El proveedor especificado no existe o no cuenta con el rol OPERADOR_SERVICIO.");
        }
        return GestionServicioRepository.save(GestionServicio);
    }
//GET SERVICIO POR ID
    public GestionServicio obtenerPorId(Long id) {
        return gestionServicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado"));
    }
//GET SERVICIO
}