package com.ReservaPro.ms_calificacion.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTests {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleCalificacionNoEncontrada_debeRetornar404() {

        CalificacionNoEncontradaException ex =
                new CalificacionNoEncontradaException("No existe");

        ResponseEntity<Map<String, Object>> resultado =
                handler.handleCalificacionNoEncontrada(ex);

        assertEquals(HttpStatus.NOT_FOUND, resultado.getStatusCode());
        assertEquals("No existe", resultado.getBody().get("message"));
    }

    @Test
    void handleIllegalArgument_debeRetornar400() {

        IllegalArgumentException ex = new IllegalArgumentException("Dato inválido");

        ResponseEntity<Map<String, Object>> resultado =
                handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("Dato inválido", resultado.getBody().get("message"));
    }

    @Test
    void handleIllegalState_debeRetornar409() {

        IllegalStateException ex = new IllegalStateException("Estado no permitido");

        ResponseEntity<Map<String, Object>> resultado =
                handler.handleIllegalState(ex);

        assertEquals(HttpStatus.CONFLICT, resultado.getStatusCode());
        assertEquals("Estado no permitido", resultado.getBody().get("message"));
    }

    @Test
    void handleValidationErrors_debeRetornar400ConMensajeDelCampo() {

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objeto", "campo", "El campo es obligatorio");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldError()).thenReturn(fieldError);

        ResponseEntity<Map<String, Object>> resultado =
                handler.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals("El campo es obligatorio", resultado.getBody().get("message"));
    }
}
