package com.ReservaPro.ms_gestion_servicio.mapper;

import com.ReservaPro.ms_gestion_servicio.dto.request.GestionServicioRequest;
import com.ReservaPro.ms_gestion_servicio.dto.response.GestionServicioResponse;
import com.ReservaPro.ms_gestion_servicio.enums.EstadoServicio;
import com.ReservaPro.ms_gestion_servicio.model.GestionServicio;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionServicioMapperTest {

    // Implementación mínima para poder invocar los métodos "default" del mapper
    // sin depender de la clase generada por el procesador de anotaciones de MapStruct.
    private final GestionServicioMapper mapper = new GestionServicioMapper() {
        @Override
        public GestionServicio toEntity(GestionServicioRequest request) {
            return null;
        }

        @Override
        public GestionServicioResponse toResponse(GestionServicio gestionServicio) {
            return null;
        }

        @Override
        public List<GestionServicioResponse> toResponseList(List<GestionServicio> gestionServicioList) {
            return null;
        }
    };

    @Test
    void stringToEstadoServicio_conNulo_retornaActivado() {
        assertEquals(EstadoServicio.ACTIVADO, mapper.stringToEstadoServicio(null));
    }

    @Test
    void stringToEstadoServicio_conVacio_retornaActivado() {
        assertEquals(EstadoServicio.ACTIVADO, mapper.stringToEstadoServicio(""));
    }

    @Test
    void stringToEstadoServicio_conValorValido_retornaEnumCorrespondiente() {
        assertEquals(EstadoServicio.MANTENIMIENTO, mapper.stringToEstadoServicio("MANTENIMIENTO"));
    }

    @Test
    void estadoServicioToString_conNulo_retornaValorActivado() {
        assertEquals(EstadoServicio.ACTIVADO.getValor(), mapper.estadoServicioToString(null));
    }

    @Test
    void estadoServicioToString_conValor_retornaSuValor() {
        assertEquals("DESACTIVADO", mapper.estadoServicioToString(EstadoServicio.DESACTIVADO));
    }
}
