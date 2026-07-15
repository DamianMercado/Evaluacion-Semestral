package com.ReservaPro.ms_pago.exception;

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
    void handlePagoNotFound_retorna404() {
        PagoNoEncontradoException ex = new PagoNoEncontradoException(1L);

        ResponseEntity<Map<String, Object>> respuesta = handler.handlePagoNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        assertEquals("Pago no encontrado", respuesta.getBody().get("error"));
        assertEquals("Pago no encontrado con ID: 1", respuesta.getBody().get("message"));
    }

    @Test
    void handlePagoNoReembolsado_retorna400() {
        PagoNoReembolsadoException ex = new PagoNoReembolsadoException("Solo se puede reembolsar un pago en estado PAGADO");

        ResponseEntity<Map<String, Object>> respuesta = handler.handlePagoNoReembolsado(ex);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("Pago no reembolsable", respuesta.getBody().get("error"));
    }

    @Test
    void handleIllegalState_retorna409() {
        IllegalStateException ex = new IllegalStateException("El pago ya está validado");

        ResponseEntity<Map<String, Object>> respuesta = handler.handleIllegalState(ex);

        assertEquals(HttpStatus.CONFLICT, respuesta.getStatusCode());
        assertEquals("Estado inválido", respuesta.getBody().get("error"));
    }

    @Test
    void handleFeignException_retorna503() {
        feign.FeignException ex = mock(feign.FeignException.class);
        org.mockito.Mockito.when(ex.getMessage()).thenReturn("Servicio no disponible");

        ResponseEntity<Map<String, Object>> respuesta = handler.handleFeignException(ex);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, respuesta.getStatusCode());
        assertEquals("Error al comunicarse con otro servicio", respuesta.getBody().get("error"));
    }

    @Test
    void handleValidationErrors_retorna400ConMapaDeErrores() {
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(new Object(), "pagoRequest");
        bindingResult.addError(new FieldError("pagoRequest", "metodoPago", "El metodoPago es obligatorio"));

        MethodParameter parameter = mock(MethodParameter.class);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);

        ResponseEntity<Map<String, Object>> respuesta = handler.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());

        @SuppressWarnings("unchecked")
        Map<String, String> errores = (Map<String, String>) respuesta.getBody().get("errores");
        assertEquals("El metodoPago es obligatorio", errores.get("metodoPago"));
    }

    @Test
    void handleGeneralError_retorna500() {
        Exception ex = new RuntimeException("Error inesperado");

        ResponseEntity<Map<String, Object>> respuesta = handler.handleGeneralError(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
        assertEquals("Error interno del servidor", respuesta.getBody().get("error"));
    }
}
