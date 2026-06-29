package com.ReservaPro.ms_reserva.servise;



import com.ReservaPro.ms_reserva.dto.response.ReservaResponse;
import com.ReservaPro.ms_reserva.enums.EstadoReserva;
import com.ReservaPro.ms_reserva.exception.ReservaNoEncontradaException;
import com.ReservaPro.ms_reserva.mapper.ReservaMapper;
import com.ReservaPro.ms_reserva.model.Reserva;
import com.ReservaPro.ms_reserva.repository.ReservaRepository;

import com.ReservaPro.ms_reserva.service.ReservaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTests {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ReservaMapper reservaMapper;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void obtenerPorId_cuandoExiste_retornaReserva() {

        Long id = 1L;

        Reserva reserva = crearReserva(id);
        ReservaResponse response = crearResponse(id);

        when(reservaRepository.findById(id))
                .thenReturn(Optional.of(reserva));

        when(reservaMapper.toResponse(reserva))
                .thenReturn(response);

        ReservaResponse resultado =
                reservaService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals(10L, resultado.getIdUsuario());
        assertEquals("PENDIENTE_PAGO", resultado.getEstadoReserva());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaException() {

        Long id = 99L;

        when(reservaRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ReservaNoEncontradaException.class,
                () -> reservaService.obtenerPorId(id)
        );
    }

    @Test
    void eliminar_cuandoExiste_eliminaReserva() {

        Long id = 1L;

        Reserva reserva = crearReserva(id);

        when(reservaRepository.findById(id))
                .thenReturn(Optional.of(reserva));

        reservaService.eliminar(id);

        verify(reservaRepository).delete(reserva);
    }

    @Test
    void eliminar_cuandoNoExiste_lanzaException() {

        Long id = 50L;

        when(reservaRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ReservaNoEncontradaException.class,
                () -> reservaService.eliminar(id)
        );

        verify(reservaRepository, never())
                .delete(any(Reserva.class));
    }

    private Reserva crearReserva(Long id) {

        Reserva reserva = new Reserva();

        reserva.setId(id);
        reserva.setIdUsuario(10L);
        reserva.setEstadoReserva(EstadoReserva.PENDIENTE_PAGO);

        return reserva;
    }

    private ReservaResponse crearResponse(Long id) {

        ReservaResponse response = new ReservaResponse();

        response.setId(id);
        response.setIdUsuario(10L);
        response.setEstadoReserva("PENDIENTE_PAGO");

        return response;
    }
}