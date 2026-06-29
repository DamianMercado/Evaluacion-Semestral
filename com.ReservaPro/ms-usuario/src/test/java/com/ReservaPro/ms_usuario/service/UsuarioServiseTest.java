package com.ReservaPro.ms_usuario.service;

import com.ReservaPro.ms_usuario.dto.response.UsuarioResponse;
import com.ReservaPro.ms_usuario.enums.RolUsuario;
import com.ReservaPro.ms_usuario.exception.UsuarioNoEncontradoException;
import com.ReservaPro.ms_usuario.mapper.UsuarioMapper;
import com.ReservaPro.ms_usuario.model.Usuario;
import com.ReservaPro.ms_usuario.repository.UsuarioRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void obtenerPorId_cuandoExiste_retornaUsuario() {

        Long id = 1L;

        Usuario usuario = crearUsuario(id);
        UsuarioResponse response = crearResponse(id);

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.of(usuario));

        when(usuarioMapper.toResponse(usuario))
                .thenReturn(response);

        UsuarioResponse resultado =
                usuarioService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Pérez", resultado.getApellido());
        assertEquals("juan@correo.cl", resultado.getEmail());
        assertEquals("12345678-9", resultado.getRut());
        assertEquals(RolUsuario.CLIENTE, resultado.getRol());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaException() {

        Long id = 99L;

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                UsuarioNoEncontradoException.class,
                () -> usuarioService.obtenerPorId(id)
        );
    }

    @Test
    void eliminar_cuandoExiste_eliminaUsuario() {

        Long id = 1L;

        Usuario usuario = crearUsuario(id);

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.of(usuario));

        usuarioService.eliminar(id);

        verify(usuarioRepository).delete(usuario);
    }

    @Test
    void eliminar_cuandoNoExiste_lanzaException() {

        Long id = 50L;

        when(usuarioRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                UsuarioNoEncontradoException.class,
                () -> usuarioService.eliminar(id)
        );

        verify(usuarioRepository, never())
                .delete(any(Usuario.class));
    }

    private Usuario crearUsuario(Long id) {

        Usuario usuario = new Usuario();

        usuario.setId(id);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setPassword("123456");
        usuario.setEmail("juan@correo.cl");
        usuario.setRut("12345678-9");
        usuario.setRol(RolUsuario.CLIENTE);

        return usuario;
    }

    private UsuarioResponse crearResponse(Long id) {

        UsuarioResponse response = new UsuarioResponse();

        response.setId(id);
        response.setNombre("Juan");
        response.setApellido("Pérez");
        response.setEmail("juan@correo.cl");
        response.setRut("12345678-9");
        response.setRol(RolUsuario.CLIENTE);

        return response;
    }
}