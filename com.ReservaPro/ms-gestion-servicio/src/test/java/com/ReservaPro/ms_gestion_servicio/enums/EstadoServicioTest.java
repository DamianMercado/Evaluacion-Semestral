package com.ReservaPro.ms_gestion_servicio.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstadoServicioTest {

    @Test
    void getValor_retornaElValorCorrecto() {
        assertEquals("ACTIVADO", EstadoServicio.ACTIVADO.getValor());
        assertEquals("DESACTIVADO", EstadoServicio.DESACTIVADO.getValor());
        assertEquals("MANTENIMIENTO", EstadoServicio.MANTENIMIENTO.getValor());
        assertEquals("ELIMINADO", EstadoServicio.ELIMINADO.getValor());
    }

    @Test
    void fromValor_conValorValidoIgnorandoMayusculas_retornaEnum() {
        assertEquals(EstadoServicio.ACTIVADO, EstadoServicio.fromValor("activado"));
        assertEquals(EstadoServicio.MANTENIMIENTO, EstadoServicio.fromValor("MANTENIMIENTO"));
    }

    @Test
    void fromValor_conValorInvalido_lanzaExcepcion() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EstadoServicio.fromValor("NO_EXISTE")
        );
        assertTrue(ex.getMessage().contains("NO_EXISTE"));
    }
}
