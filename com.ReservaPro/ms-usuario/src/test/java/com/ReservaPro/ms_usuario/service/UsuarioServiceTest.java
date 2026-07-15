package com.ReservaPro.ms_usuario.service;

import com.ReservaPro.ms_usuario.dto.request.UsuarioRequest;
import com.ReservaPro.ms_usuario.dto.response.UsuarioResponse;
import com.ReservaPro.ms_usuario.enums.RolUsuario;
import com.ReservaPro.ms_usuario.exception.UsuarioNoEncontradoException;
import com.ReservaPro.ms_usuario.mapper.UsuarioMapper;
import com.ReservaPro.ms_usuario.model.Usuario;
import com.ReservaPro.ms_usuario.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

    private Usuario usuario;
    private UsuarioRequest request;
    private UsuarioResponse response;

    @BeforeEach
    void setUp() {
        usuario = crearUsuario(1L, RolUsuario.CLIENTE);
        request = crearRequest();
        response = crearResponse(1L, RolUsuario.CLIENTE);
    }

    // ---------- obtenerTodos ----------

    @Test
    void obtenerTodos_retornaListaMapeada() {
        List<Usuario> usuarios = List.of(usuario);
        List<UsuarioResponse> responses = List.of(response);

        when(usuarioRepository.findAll()).thenReturn(usuarios);
        when(usuarioMapper.toResponseList(usuarios)).thenReturn(responses);

        List<UsuarioResponse> resultado = usuarioService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals(response, resultado.get(0));
        verify(usuarioRepository).findAll();
    }

    // ---------- obtenerPorId ----------

    @Test
    void obtenerPorId_cuandoExiste_retornaUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        UsuarioResponse resultado = usuarioService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Pérez", resultado.getApellido());
        assertEquals("juan@correo.cl", resultado.getEmail());
        assertEquals("12345678-9", resultado.getRut());
        assertEquals(RolUsuario.CLIENTE, resultado.getRol());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaException() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class,
                () -> usuarioService.obtenerPorId(99L));
    }

    // ---------- obtenerPorEmail ----------

    @Test
    void obtenerPorEmail_cuandoExiste_retornaUsuario() {
        when(usuarioRepository.findByEmail("juan@correo.cl")).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        UsuarioResponse resultado = usuarioService.obtenerPorEmail("juan@correo.cl");

        assertNotNull(resultado);
        assertEquals("juan@correo.cl", resultado.getEmail());
    }

    @Test
    void obtenerPorEmail_cuandoNoExiste_lanzaException() {
        when(usuarioRepository.findByEmail("no-existe@correo.cl")).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class,
                () -> usuarioService.obtenerPorEmail("no-existe@correo.cl"));
    }

    // ---------- obtenerPorRut ----------

    @Test
    void obtenerPorRut_cuandoExiste_retornaUsuario() {
        when(usuarioRepository.findByRut("12345678-9")).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        UsuarioResponse resultado = usuarioService.obtenerPorRut("12345678-9");

        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRut());
    }

    @Test
    void obtenerPorRut_cuandoNoExiste_lanzaException() {
        when(usuarioRepository.findByRut("0-0")).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class,
                () -> usuarioService.obtenerPorRut("0-0"));
    }

    // ---------- crear ----------

    @Test
    void crear_conDatosValidos_creaUsuario() {
        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByRut(request.getRut())).thenReturn(false);
        when(usuarioMapper.toEntity(request)).thenReturn(usuario);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hash-encriptado");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        UsuarioResponse resultado = usuarioService.crear(request);

        assertNotNull(resultado);
        assertEquals(response, resultado);
        verify(usuarioRepository).save(usuario);
        assertEquals("hash-encriptado", usuario.getPassword());
    }

    @Test
    void crear_cuandoEmailYaRegistrado_lanzaException() {
        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.crear(request));

        assertTrue(ex.getMessage().contains(request.getEmail()));
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void crear_cuandoRutYaRegistrado_lanzaException() {
        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByRut(request.getRut())).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.crear(request));

        assertTrue(ex.getMessage().contains(request.getRut()));
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void crear_conRolNulo_noLanzaErrorAlLoguear() {
        request.setRol(null);
        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByRut(request.getRut())).thenReturn(false);
        when(usuarioMapper.toEntity(request)).thenReturn(usuario);
        when(passwordEncoder.encode(anyString())).thenReturn("hash");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        assertDoesNotThrow(() -> usuarioService.crear(request));
    }

    // ---------- actualizar ----------

    @Test
    void actualizar_cuandoExisteConNuevaPassword_actualizaUsuario() {
        UsuarioRequest actualizacion = crearRequest();
        actualizacion.setNombre("Pedro");
        actualizacion.setPassword("nuevaClave123");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode("nuevaClave123")).thenReturn("hash-nueva");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        UsuarioResponse resultado = usuarioService.actualizar(1L, actualizacion);

        assertNotNull(resultado);
        assertEquals("Pedro", usuario.getNombre());
        assertEquals("hash-nueva", usuario.getPassword());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void actualizar_sinPassword_mantienePasswordActual() {
        UsuarioRequest actualizacion = crearRequest();
        actualizacion.setPassword(null);
        String passwordOriginal = usuario.getPassword();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        usuarioService.actualizar(1L, actualizacion);

        assertEquals(passwordOriginal, usuario.getPassword());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void actualizar_conPasswordVacia_mantienePasswordActual() {
        UsuarioRequest actualizacion = crearRequest();
        actualizacion.setPassword("");
        String passwordOriginal = usuario.getPassword();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        usuarioService.actualizar(1L, actualizacion);

        assertEquals(passwordOriginal, usuario.getPassword());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void actualizar_cuandoNoExiste_lanzaException() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class,
                () -> usuarioService.actualizar(99L, request));

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    // ---------- eliminar ----------

    @Test
    void eliminar_cuandoExiste_eliminaUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.eliminar(1L);

        verify(usuarioRepository).delete(usuario);
    }

    @Test
    void eliminar_cuandoNoExiste_lanzaException() {
        when(usuarioRepository.findById(50L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class,
                () -> usuarioService.eliminar(50L));

        verify(usuarioRepository, never()).delete(any(Usuario.class));
    }

    // ---------- validarCliente ----------

    @Test
    void validarCliente_cuandoEsCliente_retornaTrue() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        assertTrue(usuarioService.validarCliente(1L));
    }

    @Test
    void validarCliente_cuandoNoEsCliente_retornaFalse() {
        Usuario admin = crearUsuario(2L, RolUsuario.ADMINISTRADOR);
        UsuarioResponse adminResponse = crearResponse(2L, RolUsuario.ADMINISTRADOR);

        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(admin));
        when(usuarioMapper.toResponse(admin)).thenReturn(adminResponse);

        assertFalse(usuarioService.validarCliente(2L));
    }

    @Test
    void validarCliente_cuandoUsuarioNoExiste_retornaFalse() {
        when(usuarioRepository.findById(404L)).thenReturn(Optional.empty());

        assertFalse(usuarioService.validarCliente(404L));
    }

    // ---------- validarOperador ----------

    @Test
    void validarOperador_cuandoEsOperador_retornaTrue() {
        Usuario operador = crearUsuario(3L, RolUsuario.OPERADOR_SERVICIO);
        UsuarioResponse operadorResponse = crearResponse(3L, RolUsuario.OPERADOR_SERVICIO);

        when(usuarioRepository.findById(3L)).thenReturn(Optional.of(operador));
        when(usuarioMapper.toResponse(operador)).thenReturn(operadorResponse);

        assertTrue(usuarioService.validarOperador(3L));
    }

    @Test
    void validarOperador_cuandoNoEsOperador_retornaFalse() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        assertFalse(usuarioService.validarOperador(1L));
    }

    @Test
    void validarOperador_cuandoUsuarioNoExiste_retornaFalse() {
        when(usuarioRepository.findById(404L)).thenReturn(Optional.empty());

        assertFalse(usuarioService.validarOperador(404L));
    }

    // ---------- validarAdministrador ----------

    @Test
    void validarAdministrador_cuandoEsAdministrador_retornaTrue() {
        Usuario admin = crearUsuario(4L, RolUsuario.ADMINISTRADOR);
        UsuarioResponse adminResponse = crearResponse(4L, RolUsuario.ADMINISTRADOR);

        when(usuarioRepository.findById(4L)).thenReturn(Optional.of(admin));
        when(usuarioMapper.toResponse(admin)).thenReturn(adminResponse);

        assertTrue(usuarioService.validarAdministrador(4L));
    }

    @Test
    void validarAdministrador_cuandoNoEsAdministrador_retornaFalse() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toResponse(usuario)).thenReturn(response);

        assertFalse(usuarioService.validarAdministrador(1L));
    }

    @Test
    void validarAdministrador_cuandoUsuarioNoExiste_retornaFalse() {
        when(usuarioRepository.findById(404L)).thenReturn(Optional.empty());

        assertFalse(usuarioService.validarAdministrador(404L));
    }

    // ---------- obtenerRolPorId ----------

    @Test
    void obtenerRolPorId_cuandoExisteConRol_retornaValorDelRol() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        String rol = usuarioService.obtenerRolPorId(1L);

        assertEquals("CLIENTE", rol);
    }

    @Test
    void obtenerRolPorId_cuandoRolEsNulo_retornaTextoNull() {
        usuario.setRol(null);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        String rol = usuarioService.obtenerRolPorId(1L);

        assertEquals("null", rol);
    }

    @Test
    void obtenerRolPorId_cuandoNoExiste_lanzaException() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class,
                () -> usuarioService.obtenerRolPorId(99L));
    }

    // ---------- helpers ----------

    private Usuario crearUsuario(Long id, RolUsuario rol) {
        Usuario u = new Usuario();
        u.setId(id);
        u.setNombre("Juan");
        u.setApellido("Pérez");
        u.setPassword("123456");
        u.setEmail("juan@correo.cl");
        u.setRut("12345678-9");
        u.setRol(rol);
        return u;
    }

    private UsuarioRequest crearRequest() {
        UsuarioRequest r = new UsuarioRequest();
        r.setNombre("Juan");
        r.setApellido("Pérez");
        r.setPassword("123456");
        r.setEmail("juan@correo.cl");
        r.setRut("12345678-9");
        r.setRol(RolUsuario.CLIENTE);
        return r;
    }

    private UsuarioResponse crearResponse(Long id, RolUsuario rol) {
        UsuarioResponse resp = new UsuarioResponse();
        resp.setId(id);
        resp.setNombre("Juan");
        resp.setApellido("Pérez");
        resp.setEmail("juan@correo.cl");
        resp.setRut("12345678-9");
        resp.setRol(rol);
        return resp;
    }
}
