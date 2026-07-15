package com.ReservaPro.ms_promocion.mapper;

import com.ReservaPro.ms_promocion.dto.request.PromocionRequest;
import com.ReservaPro.ms_promocion.dto.response.PromocionResponse;
import com.ReservaPro.ms_promocion.model.Promocion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PromocionMapperTest {

    private final PromocionMapper mapper = new PromocionMapperImpl();

    @Test
    void toEntity_convierteCodigoAMayusculas() {
        PromocionRequest request = new PromocionRequest();
        request.setCodigoPromocion("verano25");
        request.setDescripcion("Descuento de verano");
        request.setPorcentajeDescuento(25.0);
        request.setActivaPromocion(true);

        Promocion promocion = mapper.toEntity(request);

        assertNotNull(promocion);
        assertEquals("VERANO25", promocion.getCodigoPromocion());
        assertEquals("Descuento de verano", promocion.getDescripcion());
        assertEquals(25.0, promocion.getPorcentajeDescuento());
        assertTrue(promocion.getActivaPromocion());
    }

    @Test
    void toEntity_conCodigoNulo_dejaCodigoNulo() {
        PromocionRequest request = new PromocionRequest();
        request.setCodigoPromocion(null);
        request.setDescripcion("Descuento");
        request.setPorcentajeDescuento(10.0);
        request.setActivaPromocion(true);

        Promocion promocion = mapper.toEntity(request);

        assertNull(promocion.getCodigoPromocion());
    }

    @Test
    void toEntity_conNull_retornaNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void toResponse_mapeaCampos() {
        Promocion promocion = new Promocion();
        promocion.setId(1L);
        promocion.setCodigoPromocion("VERANO25");
        promocion.setDescripcion("Descuento de verano");
        promocion.setPorcentajeDescuento(25.0);
        promocion.setActivaPromocion(true);

        PromocionResponse response = mapper.toResponse(promocion);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("VERANO25", response.getCodigoPromocion());
        assertEquals("Descuento de verano", response.getDescripcion());
        assertEquals(25.0, response.getPorcentajeDescuento());
        assertTrue(response.getActivaPromocion());
    }

    @Test
    void toResponse_conNull_retornaNull() {
        assertNull(mapper.toResponse(null));
    }
}
