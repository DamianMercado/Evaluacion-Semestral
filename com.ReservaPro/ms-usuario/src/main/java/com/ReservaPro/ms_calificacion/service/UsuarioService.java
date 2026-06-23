package com.ReservaPro.ms_calificacion.service;

import com.ReservaPro.ms_calificacion.dto.request.UsuarioRequest;
import com.ReservaPro.ms_calificacion.dto.response.UsuarioResponse;
import com.ReservaPro.ms_calificacion.exception.UsuarioNoEncontrado;
import com.ReservaPro.ms_calificacion.mapper.UsuarioMapper;
import com.ReservaPro.ms_calificacion.model.Usuario;
import com.ReservaPro.ms_calificacion.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final Logger log =
            LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public List<UsuarioResponse> obtener() {

        log.info("Obteniendo todos los usuarios");

        return usuarioMapper.toResponseList(
                usuarioRepository.findAll()
        );
    }

    public UsuarioResponse obtenerPorId(Long id) {

        log.info("Buscando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNoEncontrado(id)
                );

        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse crear(UsuarioRequest request) {

        log.info(
                "Creando usuario con email: {}",
                request.getEmail()
        );

        Usuario usuario =
                usuarioMapper.toEntity(request);

        Usuario guardado =
                usuarioRepository.save(usuario);

        log.info(
                "Usuario creado correctamente con ID: {}",
                guardado.getId()
        );

        return usuarioMapper.toResponse(guardado);
    }

    public UsuarioResponse actualizar(
            Long id,
            UsuarioRequest request) {

        log.info("Actualizando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNoEncontrado(id)
                );

        usuario.setNombre(
                request.getNombre()
        );

        usuario.setApellido(
                request.getApellido()
        );

        usuario.setPassword(
                request.getPassword()
        );

        usuario.setEmail(
                request.getEmail()
        );

        usuario.setRut(
                request.getRut()
        );

        usuario.setRol(
                request.getRol()
        );

        Usuario actualizado =
                usuarioRepository.save(usuario);

        log.info(
                "Usuario actualizado correctamente con ID: {}",
                id
        );

        return usuarioMapper.toResponse(actualizado);
    }

    public void eliminar(Long id) {

        log.info("Eliminando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNoEncontrado(id)
                );

        usuarioRepository.delete(usuario);

        log.info(
                "Usuario eliminado correctamente con ID: {}",
                id
        );
    }
}