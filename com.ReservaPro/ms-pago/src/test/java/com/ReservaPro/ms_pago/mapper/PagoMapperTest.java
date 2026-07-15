package com.ReservaPro.ms_pago.mapper;

import com.ReservaPro.ms_pago.dto.request.PagoRequest;
import com.ReservaPro.ms_pago.dto.response.PagoResponse;
import com.ReservaPro.ms_pago.enums.Estado;
import com.ReservaPro.ms_pago.enums.TipoBanco;
import com.ReservaPro.ms_pago.model.Pago;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PagoMapperTest {

    private final PagoMapper mapper = new PagoMapperImpl();

    @Test
    void toEntity_creaPagoPendienteSinDescuentoYSinId() {
        PagoRequest request = new PagoRequest();
        request.setIdReserva(10L);
        request.setMontoOriginal(10000.0);
        request.setMontoPago(10000.0);
        request.setMetodoPago("DEBITO");
        request.setTipoBanco(TipoBanco.BANCO_ESTADO);
        request.setCodigoPromocion("PROMO10");

        Pago pago = mapper.toEntity(request);

        assertNotNull(pago);
        assertNull(pago.getId());
        assertEquals(10L, pago.getIdReserva());
        assertEquals(10000.0, pago.getMontoOriginal());
        assertEquals(10000.0, pago.getMontoPago());
        assertEquals("DEBITO", pago.getMetodoPago());
        assertEquals(TipoBanco.BANCO_ESTADO, pago.getTipoBanco());
        assertEquals(Estado.PENDIENTE, pago.getEstadoPago());
        assertFalse(pago.isAplicaDescuento());
        assertNull(pago.getFechaPago());
    }

    @Test
    void toEntity_conNull_retornaNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void toResponse_mapeaCamposYDejaDescuentoNulo() {
        Pago pago = Pago.builder()
                .id(1L)
                .idReserva(10L)
                .montoOriginal(10000.0)
                .montoPago(8000.0)
                .metodoPago("CREDITO")
                .tipoBanco(TipoBanco.CHILE)
                .estadoPago(Estado.PAGADO)
                .fechaPago(LocalDate.of(2026, 1, 1))
                .aplicaDescuento(true)
                .codigoPromocion("PROMO20")
                .build();

        PagoResponse response = mapper.toResponse(pago);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(10L, response.getIdReserva());
        assertEquals(8000.0, response.getMontoPago());
        assertEquals("CREDITO", response.getMetodoPago());
        assertEquals(TipoBanco.CHILE, response.getTipoBanco());
        assertEquals(Estado.PAGADO, response.getEstadoPago());
        assertEquals(LocalDate.of(2026, 1, 1), response.getFechaPago());
        assertEquals("PROMO20", response.getCodigoPromocion());
        assertNull(response.getDescuento());
    }

    @Test
    void toResponse_conNull_retornaNull() {
        assertNull(mapper.toResponse(null));
    }

    @Test
    void estadoToString_conEstado_retornaValor() {
        assertEquals("PAGADO", mapper.estadoToString(Estado.PAGADO));
    }

    @Test
    void estadoToString_conNull_retornaNull() {
        assertNull(mapper.estadoToString(null));
    }
}
