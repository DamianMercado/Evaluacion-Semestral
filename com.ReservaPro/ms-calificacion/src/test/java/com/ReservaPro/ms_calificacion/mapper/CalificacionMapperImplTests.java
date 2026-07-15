package com.ReservaPro.ms_calificacion.mapper;

import com.ReservaPro.ms_calificacion.dto.request.CalificacionRequest;
import com.ReservaPro.ms_calificacion.dto.response.CalificacionResponse;
import com.ReservaPro.ms_calificacion.enums.EstadoCalificacion;
import com.ReservaPro.ms_calificacion.model.Calificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalificacionMapperImplTests {

    private final CalificacionMapper mapper = new CalificacionMapperImpl();

    private Calificacion calificacion;

    @BeforeEach
    void setUp() {
        calificacion = Calificacion.builder()
                .id(1L)
                .idReserva(20L)
                .idUsuario(10L)
                .puntuacion(5)
                .comentario("Excelente servicio")
                .estado(EstadoCalificacion.PUBLICADA)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();
    }

    @Test
    void toEntity_debeMapearCamposYForzarEstadoPendiente() {

        CalificacionRequest request = new CalificacionRequest();
        request.setIdReserva(20L);
        request.setIdUsuario(10L);
        request.setPuntuacion(5);
        request.setComentario("Excelente servicio");

        Calificacion entidad = mapper.toEntity(request);

        assertNotNull(entidad);
        assertEquals(20L, entidad.getIdReserva());
        assertEquals(10L, entidad.getIdUsuario());
        assertEquals(5, entidad.getPuntuacion());
        assertEquals("Excelente servicio", entidad.getComentario());
        assertEquals(EstadoCalificacion.PENDIENTE, entidad.getEstado());
        assertNull(entidad.getId());
    }

    @Test
    void toEntity_conRequestNulo_debeRetornarNulo() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void toResponse_debeMapearTodosLosCampos() {

        CalificacionResponse response = mapper.toResponse(calificacion);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(20L, response.getIdReserva());
        assertEquals(10L, response.getIdUsuario());
        assertEquals(5, response.getPuntuacion());
        assertEquals("Excelente servicio", response.getComentario());
        assertEquals(EstadoCalificacion.PUBLICADA, response.getEstado());
        assertNotNull(response.getFechaCreacion());
        assertNotNull(response.getFechaActualizacion());
    }

    @Test
    void toResponse_conNulo_debeRetornarNulo() {
        assertNull(mapper.toResponse(null));
    }

    @Test
    void toResponseList_debeMapearListaCompleta() {

        List<CalificacionResponse> lista = mapper.toResponseList(List.of(calificacion));

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals(1L, lista.get(0).getId());
    }

    @Test
    void toResponseList_conNulo_debeRetornarNulo() {
        assertNull(mapper.toResponseList(null));
    }
}
