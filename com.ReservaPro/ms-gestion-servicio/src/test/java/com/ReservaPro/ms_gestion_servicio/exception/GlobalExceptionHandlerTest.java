package com.ReservaPro.ms_gestion_servicio.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleGestionServicioNotFound_retorna404ConMensaje() {
        GestionServicioNotFoundException ex = new GestionServicioNotFoundException(99L);

        ResponseEntity<Map<String, Object>> resultado = handler.handleGestionServicioNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, resultado.getStatusCode());
        assertEquals("Servicio no encontrado", resultado.getBody().get("error"));
        assertEquals(ex.getMessage(), resultado.getBody().get("message"));
    }

    @Test
    void handleIllegalArgument_retorna400ConMensaje() {
        IllegalArgumentException ex = new IllegalArgumentException("Ya existe un servicio con el nombre: X");

        ResponseEntity<Map<String, Object>> resultado = handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("Datos inválidos", resultado.getBody().get("error"));
        assertEquals(ex.getMessage(), resultado.getBody().get("message"));
    }

    @Test
    void handleValidationErrors_retorna400ConErroresDeCampo() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("gestionServicioRequest", "nombre", "El nombre es obligatorio");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<Map<String, Object>> resultado = handler.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("Datos inválidos", resultado.getBody().get("error"));

        @SuppressWarnings("unchecked")
        Map<String, String> errores = (Map<String, String>) resultado.getBody().get("errores");
        assertEquals("El nombre es obligatorio", errores.get("nombre"));
    }

    @Test
    void handleGeneralError_retorna500ConMensaje() {
        Exception ex = new RuntimeException("Fallo inesperado");

        ResponseEntity<Map<String, Object>> resultado = handler.handleGeneralError(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resultado.getStatusCode());
        assertEquals("Error interno del servidor", resultado.getBody().get("error"));
        assertEquals("Fallo inesperado", resultado.getBody().get("message"));
    }
}
