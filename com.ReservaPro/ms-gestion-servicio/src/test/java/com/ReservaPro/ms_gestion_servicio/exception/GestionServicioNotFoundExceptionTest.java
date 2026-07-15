package com.ReservaPro.ms_gestion_servicio.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestionServicioNotFoundExceptionTest {

    @Test
    void constructorConId_generaMensajePorDefecto() {
        GestionServicioNotFoundException ex = new GestionServicioNotFoundException(5L);
        assertEquals("Servicio no encontrado con ID: 5", ex.getMessage());
    }

    @Test
    void constructorConMensaje_usaMensajePersonalizado() {
        GestionServicioNotFoundException ex = new GestionServicioNotFoundException("mensaje custom");
        assertEquals("mensaje custom", ex.getMessage());
    }

    @Test
    void constructorConMensajeYCausa_propagaCausa() {
        Throwable causa = new RuntimeException("causa raiz");
        GestionServicioNotFoundException ex = new GestionServicioNotFoundException("mensaje custom", causa);

        assertEquals("mensaje custom", ex.getMessage());
        assertEquals(causa, ex.getCause());
    }
}
