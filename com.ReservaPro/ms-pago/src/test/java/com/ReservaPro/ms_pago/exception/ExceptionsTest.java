package com.ReservaPro.ms_pago.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionsTest {

    @Test
    void pagoNoEncontradoException_conId_construyeMensajePorDefecto() {
        PagoNoEncontradoException ex = new PagoNoEncontradoException(5L);
        assertEquals("Pago no encontrado con ID: 5", ex.getMessage());
    }

    @Test
    void pagoNoEncontradoException_conMensajePersonalizado_usaEseMensaje() {
        PagoNoEncontradoException ex = new PagoNoEncontradoException("mensaje personalizado");
        assertEquals("mensaje personalizado", ex.getMessage());
    }

    @Test
    void pagoNoReembolsadoException_construyeConMensaje() {
        PagoNoReembolsadoException ex = new PagoNoReembolsadoException("no reembolsable");
        assertEquals("no reembolsable", ex.getMessage());
    }
}
