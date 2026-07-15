package com.ReservaPro.ms_cancelacion.mapper;

import com.ReservaPro.ms_cancelacion.dto.request.CancelacionRequest;
import com.ReservaPro.ms_cancelacion.dto.response.CancelacionResponse;
import com.ReservaPro.ms_cancelacion.model.Cancelacion;
import com.ReservaPro.ms_cancelacion.model.EstadoReembolso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CancelacionMapperImplTests {

    private final CancelacionMapper mapper = new CancelacionMapperImpl();

    private Cancelacion cancelacion;

    @BeforeEach
    void setUp() {
        cancelacion = new Cancelacion();
        cancelacion.setIdCancelacion(1L);
        cancelacion.setMotivo("Cambio de planes");
        cancelacion.setFechaCancelacion(LocalDate.now());
        cancelacion.setEstadoReembolso(EstadoReembolso.PENDIENTE);
        cancelacion.setIdReserva(20L);
    }

    @Test
    void toEntity_debeMapearCamposDesdeRequest() {

        CancelacionRequest request = new CancelacionRequest();
        request.setMotivo("Cambio de planes");
        request.setFechaCancelacion(LocalDate.now());
        request.setEstadoReembolso(EstadoReembolso.PENDIENTE);
        request.setIdReserva(20L);

        Cancelacion entidad = mapper.toEntity(request);

        assertNotNull(entidad);
        assertEquals("Cambio de planes", entidad.getMotivo());
        assertEquals(EstadoReembolso.PENDIENTE, entidad.getEstadoReembolso());
        assertEquals(20L, entidad.getIdReserva());
        assertNull(entidad.getIdCancelacion());
    }

    @Test
    void toEntity_conRequestNulo_debeRetornarNulo() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void toResponse_debeMapearTodosLosCampos() {

        CancelacionResponse response = mapper.toResponse(cancelacion);

        assertNotNull(response);
        assertEquals(1L, response.getIdCancelacion());
        assertEquals("Cambio de planes", response.getMotivo());
        assertEquals(EstadoReembolso.PENDIENTE, response.getEstadoReembolso());
        assertEquals(20L, response.getIdReserva());
    }

    @Test
    void toResponse_conNulo_debeRetornarNulo() {
        assertNull(mapper.toResponse(null));
    }

    @Test
    void toResponseList_debeMapearListaCompleta() {

        List<CancelacionResponse> lista = mapper.toResponseList(List.of(cancelacion));

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals(1L, lista.get(0).getIdCancelacion());
    }

    @Test
    void toResponseList_conNulo_debeRetornarNulo() {
        assertNull(mapper.toResponseList(null));
    }
}
