package com.ReservaPro.ms_cancelacion.exception;

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

class GlobalExceptionHandlerTests {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFound_debeRetornar404() {

        CancelacionNotFoundException ex = new CancelacionNotFoundException(1L);

        ResponseEntity<Map<String, Object>> resultado = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, resultado.getStatusCode());
        assertEquals(404, resultado.getBody().get("status"));
        assertTrue(((String) resultado.getBody().get("message")).contains("1"));
    }

    @Test
    void handleValidation_debeRetornar400ConPrimerMensaje() {

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objeto", "motivo", "El motivo es obligatorio");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<Map<String, Object>> resultado = handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, resultado.getStatusCode());
        assertEquals(400, resultado.getBody().get("status"));
        assertEquals("El motivo es obligatorio", resultado.getBody().get("message"));
    }
}
