package com.ReservaPro.ms_usuario.controller;

import com.ReservaPro.ms_usuario.dto.request.UsuarioRequest;
import com.ReservaPro.ms_usuario.dto.response.UsuarioResponse;
import com.ReservaPro.ms_usuario.enums.RolUsuario;
import com.ReservaPro.ms_usuario.exception.GlobalExceptionHandler;
import com.ReservaPro.ms_usuario.exception.UsuarioNoEncontradoException;
import com.ReservaPro.ms_usuario.service.UsuarioService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private UsuarioResponse response;
    private UsuarioRequest request;

    @BeforeEach
    void setUp() {
        UsuarioController controller = new UsuarioController(usuarioService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        response = new UsuarioResponse();
        response.setId(1L);
        response.setNombre("Juan");
        response.setApellido("Pérez");
        response.setEmail("juan@correo.cl");
        response.setRut("12345678-9");
        response.setRol(RolUsuario.CLIENTE);

        request = new UsuarioRequest();
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setPassword("123456");
        request.setEmail("juan@correo.cl");
        request.setRut("12345678-9");
        request.setRol(RolUsuario.CLIENTE);
    }

    @Test
    void obtenerTodos_retorna200ConLista() throws Exception {
        when(usuarioService.obtenerTodos()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("juan@correo.cl"));
    }

    @Test
    void obtenerPorId_cuandoExiste_retorna200() throws Exception {
        when(usuarioService.obtenerPorId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/usuarios/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void obtenerPorId_cuandoNoExiste_retorna404() throws Exception {
        when(usuarioService.obtenerPorId(99L))
                .thenThrow(new UsuarioNoEncontradoException("Usuario no encontrado con ID: 99"));

        mockMvc.perform(get("/api/v1/usuarios/{id}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Usuario no encontrado"));
    }

    @Test
    void obtenerPorEmail_retorna200() throws Exception {
        when(usuarioService.obtenerPorEmail("juan@correo.cl")).thenReturn(response);

        mockMvc.perform(get("/api/v1/usuarios/email/{email}", "juan@correo.cl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("juan@correo.cl"));
    }

    @Test
    void obtenerPorRut_retorna200() throws Exception {
        when(usuarioService.obtenerPorRut("12345678-9")).thenReturn(response);

        mockMvc.perform(get("/api/v1/usuarios/rut/{rut}", "12345678-9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rut").value("12345678-9"));
    }

    @Test
    void obtenerRol_retorna200() throws Exception {
        when(usuarioService.obtenerRolPorId(1L)).thenReturn("CLIENTE");

        mockMvc.perform(get("/api/v1/usuarios/{id}/rol", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("CLIENTE"));
    }

    @Test
    void crear_conDatosValidos_retorna201() throws Exception {
        when(usuarioService.crear(any(UsuarioRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("juan@correo.cl"));
    }

    @Test
    void crear_conDatosInvalidos_retorna400() throws Exception {
        request.setNombre("");
        request.setEmail("no-es-un-email");

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_retorna200() throws Exception {
        when(usuarioService.actualizar(eq(1L), any(UsuarioRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/usuarios/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void actualizar_cuandoNoExiste_retorna404() throws Exception {
        when(usuarioService.actualizar(eq(99L), any(UsuarioRequest.class)))
                .thenThrow(new UsuarioNoEncontradoException("Usuario no encontrado con ID: 99"));

        mockMvc.perform(put("/api/v1/usuarios/{id}", 99L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_retorna204() throws Exception {
        mockMvc.perform(delete("/api/v1/usuarios/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void validarCliente_retorna200() throws Exception {
        when(usuarioService.validarCliente(anyLong())).thenReturn(true);

        mockMvc.perform(get("/api/v1/usuarios/{id}/validar-cliente", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void validarOperador_retorna200() throws Exception {
        when(usuarioService.validarOperador(anyLong())).thenReturn(false);

        mockMvc.perform(get("/api/v1/usuarios/{id}/validar-operador", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void validarAdministrador_retorna200() throws Exception {
        when(usuarioService.validarAdministrador(anyLong())).thenReturn(true);

        mockMvc.perform(get("/api/v1/usuarios/{id}/validar-administrador", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
