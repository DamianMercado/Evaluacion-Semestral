package com.ReservaPro.ms_usuario.service;

import com.ReservaPro.ms_usuario.dto.request.UsuarioRequest;
import com.ReservaPro.ms_usuario.dto.response.UsuarioResponse;
import com.ReservaPro.ms_usuario.enums.RolUsuario;
import com.ReservaPro.ms_usuario.exception.UsuarioNoEncontradoException;
import com.ReservaPro.ms_usuario.mapper.UsuarioMapper;
import com.ReservaPro.ms_usuario.model.Usuario;
import com.ReservaPro.ms_usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UsuarioResponse> obtenerTodos() {
        log.info("Obteniendo todos los usuarios");
        return usuarioMapper.toResponseList(usuarioRepository.findAll());
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorId(Long id) {
        log.info("Buscando usuario con ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + id));
        return usuarioMapper.toResponse(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorEmail(String email) {
        log.info("Buscando usuario con email: {}", email);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con email: " + email));
        return usuarioMapper.toResponse(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorRut(String rut) {
        log.info("Buscando usuario con RUT: {}", rut);
        Usuario usuario = usuarioRepository.findByRut(rut)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con RUT: " + rut));
        return usuarioMapper.toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse crear(UsuarioRequest request) {
        log.info("Creando usuario con email: {} - Rol: {}",
                request.getEmail(),
                request.getRol() != null ? request.getRol().getValor() : "null");

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email '" + request.getEmail() + "' ya está registrado");
        }

        if (usuarioRepository.existsByRut(request.getRut())) {
            throw new IllegalArgumentException("El RUT '" + request.getRut() + "' ya está registrado");
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        String passwordEncriptada = passwordEncoder.encode(request.getPassword());
        usuario.setPassword(passwordEncriptada);

        Usuario guardado = usuarioRepository.save(usuario);
        log.info("Usuario creado correctamente con ID: {} - Rol: {}",
                guardado.getId(),
                guardado.getRol() != null ? guardado.getRol().getValor() : "null");

        return usuarioMapper.toResponse(guardado);
    }

    @Transactional
    public UsuarioResponse actualizar(Long id, UsuarioRequest request) {
        log.info("Actualizando usuario con ID: {} - Nuevo Rol: {}",
                id,
                request.getRol() != null ? request.getRol().getValor() : "null");

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + id));

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setRut(request.getRut());
        usuario.setRol(request.getRol());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            String passwordEncriptada = passwordEncoder.encode(request.getPassword());
            usuario.setPassword(passwordEncriptada);
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        log.info("Usuario actualizado correctamente con ID: {} - Rol: {}",
                id,
                actualizado.getRol() != null ? actualizado.getRol().getValor() : "null");

        return usuarioMapper.toResponse(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + id));

        log.info("Usuario eliminado: {} - Rol: {}",
                usuario.getEmail(),
                usuario.getRol() != null ? usuario.getRol().getValor() : "null");

        usuarioRepository.delete(usuario);
        log.info("Usuario eliminado correctamente con ID: {}", id);
    }

    @Transactional(readOnly = true)
    public boolean validarCliente(Long id) {
        try {
            UsuarioResponse usuario = obtenerPorId(id);
            boolean esCliente = usuario.getRol() == RolUsuario.CLIENTE;
            log.info("Validando cliente ID: {} - Resultado: {} - Rol: {}",
                    id,
                    esCliente,
                    usuario.getRol() != null ? usuario.getRol().getValor() : "null");
            return esCliente;
        } catch (Exception e) {
            log.warn("Error al validar cliente ID: {} - {}", id, e.getMessage());
            return false;
        }
    }

    @Transactional(readOnly = true)
    public boolean validarOperador(Long id) {
        try {
            UsuarioResponse usuario = obtenerPorId(id);
            boolean esOperador = usuario.getRol() == RolUsuario.OPERADOR_SERVICIO;
            log.info("Validando operador ID: {} - Resultado: {} - Rol: {}",
                    id,
                    esOperador,
                    usuario.getRol() != null ? usuario.getRol().getValor() : "null");
            return esOperador;
        } catch (Exception e) {
            log.warn("Error al validar operador ID: {} - {}", id, e.getMessage());
            return false;
        }
    }

    @Transactional(readOnly = true)
    public boolean validarAdministrador(Long id) {
        try {
            UsuarioResponse usuario = obtenerPorId(id);
            boolean esAdmin = usuario.getRol() == RolUsuario.ADMINISTRADOR;
            log.info("Validando administrador ID: {} - Resultado: {} - Rol: {}",
                    id,
                    esAdmin,
                    usuario.getRol() != null ? usuario.getRol().getValor() : "null");
            return esAdmin;
        } catch (Exception e) {
            log.warn("Error al validar administrador ID: {} - {}", id, e.getMessage());
            return false;
        }
    }

    @Transactional(readOnly = true)
    public String obtenerRolPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + id));
        return usuario.getRol() != null ? usuario.getRol().getValor() : "null";
    }
}