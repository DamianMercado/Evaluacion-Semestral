package com.ReservaPro.ms_usuario.exception;

import org.junit.jupiter.api.Test;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleUsuarioNoEncontrado_retorna404() {
        UsuarioNoEncontradoException ex = new UsuarioNoEncontradoException("Usuario no encontrado con ID: 1");

        ResponseEntity<Map<String, Object>> respuesta = handler.handleUsuarioNoEncontrado(ex);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        assertEquals("Usuario no encontrado", respuesta.getBody().get("error"));
        assertEquals("Usuario no encontrado con ID: 1", respuesta.getBody().get("message"));
    }

    @Test
    void handleIllegalArgument_retorna400() {
        IllegalArgumentException ex = new IllegalArgumentException("El email ya está registrado");

        ResponseEntity<Map<String, Object>> respuesta = handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("Datos inválidos", respuesta.getBody().get("error"));
        assertEquals("El email ya está registrado", respuesta.getBody().get("message"));
    }

    @Test
    void handleValidationErrors_retorna400ConMensajeDelCampo() {
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(new Object(), "usuarioRequest");
        bindingResult.addError(new FieldError("usuarioRequest", "nombre", "El nombre es obligatorio"));

        MethodParameter parameter = mock(MethodParameter.class);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);

        ResponseEntity<Map<String, Object>> respuesta = handler.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("El nombre es obligatorio", respuesta.getBody().get("message"));
    }

    @Test
    void handleGeneralError_retorna500() {
        Exception ex = new RuntimeException("Error inesperado");

        ResponseEntity<Map<String, Object>> respuesta = handler.handleGeneralError(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
        assertEquals("Error interno del servidor", respuesta.getBody().get("error"));
        assertEquals("Error inesperado", respuesta.getBody().get("message"));
    }
}
