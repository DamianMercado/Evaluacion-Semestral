package com.ReservaPro.ms_promocion.exception;

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
    void handleValidationErrors_retorna400ConMapaDeErroresPorCampo() {
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(new Object(), "promocionRequest");
        bindingResult.addError(new FieldError("promocionRequest", "descripcion", "La descricpion no puede estar vacía"));
        bindingResult.addError(new FieldError("promocionRequest", "porcentajeDescuento", "El porcentaje de descuento no puede estar vacio"));

        MethodParameter parameter = mock(MethodParameter.class);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);

        ResponseEntity<?> respuesta = handler.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) respuesta.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("status"));

        @SuppressWarnings("unchecked")
        Map<String, String> errores = (Map<String, String>) body.get("errores");
        assertEquals("La descricpion no puede estar vacía", errores.get("descripcion"));
        assertEquals("El porcentaje de descuento no puede estar vacio", errores.get("porcentajeDescuento"));
    }
}
