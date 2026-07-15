package com.ReservaPro.ms_pago.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumsTest {

    @Test
    void estado_getValor_retornaElValorCorrecto() {
        assertEquals("PENDIENTE", Estado.PENDIENTE.getValor());
        assertEquals("PAGADO", Estado.PAGADO.getValor());
        assertEquals("FALLIDO", Estado.FALLIDO.getValor());
        assertEquals("REEMBOLSO", Estado.REEMBOLSO.getValor());
    }

    @Test
    void estado_fromValor_esInsensibleAMayusculas() {
        assertEquals(Estado.PAGADO, Estado.fromValor("pagado"));
    }

    @Test
    void estado_fromValor_conValorDesconocido_lanzaException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Estado.fromValor("DESCONOCIDO"));
        assertTrue(ex.getMessage().contains("DESCONOCIDO"));
    }

    @Test
    void metodo_getValor_retornaElValorCorrecto() {
        assertEquals("CREDITO", Metodo.CREDITO.getValor());
        assertEquals("DEBITO", Metodo.DEBITO.getValor());
        assertEquals("EFECTIVO", Metodo.EFECTIVO.getValor());
    }

    @Test
    void metodo_fromValor_esInsensibleAMayusculas() {
        assertEquals(Metodo.EFECTIVO, Metodo.fromValor("efectivo"));
    }

    @Test
    void metodo_fromValor_conValorDesconocido_lanzaException() {
        assertThrows(IllegalArgumentException.class, () -> Metodo.fromValor("CHEQUE"));
    }

    @Test
    void tipoBanco_getValor_retornaElValorCorrecto() {
        assertEquals("BANCO_ESTADO", TipoBanco.BANCO_ESTADO.getValor());
        assertEquals("SANTANDER", TipoBanco.SANTANDER.getValor());
    }

    @Test
    void tipoBanco_fromValor_esInsensibleAMayusculas() {
        assertEquals(TipoBanco.BCI, TipoBanco.fromValor("bci"));
    }

    @Test
    void tipoBanco_fromValor_conValorDesconocido_lanzaException() {
        assertThrows(IllegalArgumentException.class, () -> TipoBanco.fromValor("BANCO_FALSO"));
    }
}
