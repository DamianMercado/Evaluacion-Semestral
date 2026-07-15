package com.ReservaPro.ms_reserva.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservaNoEncontradaExceptionTest {

    @Test
    void constructorConId_generaMensajePorDefecto() {
        ReservaNoEncontradaException ex = new ReservaNoEncontradaException(5L);
        assertEquals("Reserva no encontrada con ID: 5", ex.getMessage());
    }

    @Test
    void constructorConMensaje_usaMensajePersonalizado() {
        ReservaNoEncontradaException ex = new ReservaNoEncontradaException("mensaje custom");
        assertEquals("mensaje custom", ex.getMessage());
    }

    @Test
    void constructorConMensajeYCausa_propagaCausa() {
        Throwable causa = new RuntimeException("causa raiz");
        ReservaNoEncontradaException ex = new ReservaNoEncontradaException("mensaje custom", causa);

        assertEquals("mensaje custom", ex.getMessage());
        assertEquals(causa, ex.getCause());
    }

    @Test
    void apiError_builderConstruyeCorrectamente() {
        LocalDateTime ahora = LocalDateTime.now();

        ApiError error = ApiError.builder()
                .timestamp(ahora)
                .status(404)
                .error("Reserva no encontrada")
                .message("Reserva no encontrada con ID: 1")
                .errors(List.of("detalle 1"))
                .path("/api/v1/reservas/1")
                .build();

        assertEquals(ahora, error.getTimestamp());
        assertEquals(404, error.getStatus());
        assertEquals("Reserva no encontrada", error.getError());
        assertEquals("Reserva no encontrada con ID: 1", error.getMessage());
        assertEquals(1, error.getErrors().size());
        assertEquals("/api/v1/reservas/1", error.getPath());
    }
}
