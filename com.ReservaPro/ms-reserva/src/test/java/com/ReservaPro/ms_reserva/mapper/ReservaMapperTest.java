package com.ReservaPro.ms_reserva.mapper;

import com.ReservaPro.ms_reserva.dto.request.ReservaRequest;
import com.ReservaPro.ms_reserva.dto.response.ReservaResponse;
import com.ReservaPro.ms_reserva.enums.EstadoReserva;
import com.ReservaPro.ms_reserva.model.Reserva;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservaMapperTest {

    // Implementación mínima para invocar el método "default" del mapper
    // sin depender de la clase generada por el procesador de anotaciones de MapStruct.
    private final ReservaMapper mapper = new ReservaMapper() {
        @Override
        public Reserva toEntity(ReservaRequest request) {
            return null;
        }

        @Override
        public ReservaResponse toResponse(Reserva reserva) {
            return null;
        }

        @Override
        public List<ReservaResponse> toResponseList(List<Reserva> reservas) {
            return null;
        }
    };

    @Test
    void estadoReservaToString_conNulo_retornaNulo() {
        assertNull(mapper.estadoReservaToString(null));
    }

    @Test
    void estadoReservaToString_conValor_retornaSuValor() {
        assertEquals("CONFIRMADA", mapper.estadoReservaToString(EstadoReserva.CONFIRMADA));
    }
}
