package com.ReservaPro.ms_reserva.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstadoReservaTest {

    @Test
    void getValor_retornaElValorCorrecto() {
        assertEquals("PENDIENTE_PAGO", EstadoReserva.PENDIENTE_PAGO.getValor());
        assertEquals("PAGADO", EstadoReserva.PAGADO.getValor());
        assertEquals("CONFIRMADA", EstadoReserva.CONFIRMADA.getValor());
        assertEquals("CANCELADA", EstadoReserva.CANCELADA.getValor());
        assertEquals("COMPLETADA", EstadoReserva.COMPLETADA.getValor());
    }

    @Test
    void fromValor_conValorValidoIgnorandoMayusculas_retornaEnum() {
        assertEquals(EstadoReserva.PAGADO, EstadoReserva.fromValor("pagado"));
        assertEquals(EstadoReserva.CONFIRMADA, EstadoReserva.fromValor("CONFIRMADA"));
    }

    @Test
    void fromValor_conValorInvalido_lanzaExcepcion() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EstadoReserva.fromValor("NO_EXISTE")
        );
        assertTrue(ex.getMessage().contains("NO_EXISTE"));
    }

    @Test
    void puedeTransicionarA_desdePendientePago() {
        assertTrue(EstadoReserva.PENDIENTE_PAGO.puedeTransicionarA(EstadoReserva.PAGADO));
        assertTrue(EstadoReserva.PENDIENTE_PAGO.puedeTransicionarA(EstadoReserva.CANCELADA));
        assertFalse(EstadoReserva.PENDIENTE_PAGO.puedeTransicionarA(EstadoReserva.CONFIRMADA));
        assertFalse(EstadoReserva.PENDIENTE_PAGO.puedeTransicionarA(EstadoReserva.COMPLETADA));
    }

    @Test
    void puedeTransicionarA_desdePagado() {
        assertTrue(EstadoReserva.PAGADO.puedeTransicionarA(EstadoReserva.CONFIRMADA));
        assertTrue(EstadoReserva.PAGADO.puedeTransicionarA(EstadoReserva.CANCELADA));
        assertFalse(EstadoReserva.PAGADO.puedeTransicionarA(EstadoReserva.COMPLETADA));
    }

    @Test
    void puedeTransicionarA_desdeConfirmada() {
        assertTrue(EstadoReserva.CONFIRMADA.puedeTransicionarA(EstadoReserva.COMPLETADA));
        assertTrue(EstadoReserva.CONFIRMADA.puedeTransicionarA(EstadoReserva.CANCELADA));
        assertFalse(EstadoReserva.CONFIRMADA.puedeTransicionarA(EstadoReserva.PAGADO));
    }

    @Test
    void puedeTransicionarA_desdeEstadosFinales_siempreFalse() {
        for (EstadoReserva destino : EstadoReserva.values()) {
            assertFalse(EstadoReserva.COMPLETADA.puedeTransicionarA(destino));
            assertFalse(EstadoReserva.CANCELADA.puedeTransicionarA(destino));
        }
    }
}
