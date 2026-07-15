package com.ReservaPro.ms_usuario.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RolUsuarioTest {

    @Test
    void getValor_retornaElValorCorrecto() {
        assertEquals("ADMINISTRADOR", RolUsuario.ADMINISTRADOR.getValor());
        assertEquals("CLIENTE", RolUsuario.CLIENTE.getValor());
        assertEquals("OPERADOR_SERVICIO", RolUsuario.OPERADOR_SERVICIO.getValor());
    }

    @Test
    void fromValor_conValorExacto_retornaElRol() {
        assertEquals(RolUsuario.CLIENTE, RolUsuario.fromValor("CLIENTE"));
    }

    @Test
    void fromValor_esInsensibleAMayusculas() {
        assertEquals(RolUsuario.ADMINISTRADOR, RolUsuario.fromValor("administrador"));
    }

    @Test
    void fromValor_conValorDesconocido_lanzaException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> RolUsuario.fromValor("SUPERUSUARIO"));

        assertTrue(ex.getMessage().contains("SUPERUSUARIO"));
    }
}
