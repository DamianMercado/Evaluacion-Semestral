package com.ReservaPro.ms_historial_reserva.service;

import com.ReservaPro.ms_historial_reserva.dto.response.HistorialReservaResponse;
import com.ReservaPro.ms_historial_reserva.exception.HistorialReservaNoEncontradaException;
import com.ReservaPro.ms_historial_reserva.mapper.HistorialReservaMapper;
import com.ReservaPro.ms_historial_reserva.model.EstadoReserva;
import com.ReservaPro.ms_historial_reserva.model.HistorialReserva;
import com.ReservaPro.ms_historial_reserva.repository.HistorialReservaRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistorialReservaTests {

    @Mock
    private HistorialReservaRepository historialReservaRepository;

    @Mock
    private HistorialReservaMapper historialReservaMapper;

    @InjectMocks
    private HistorialReservaService historialReservaService;

    @Test
    void obtenerPorId_cuandoExiste_retornaHistorial() {

        // Given
        Long id = 1L;
        HistorialReserva historial = crearHistorial(id);
        HistorialReservaResponse response = crearResponse(id);

        when(historialReservaRepository.findById(id))
                .thenReturn(Optional.of(historial));

        when(historialReservaMapper.toResponse(historial))
                .thenReturn(response);

        // When
        HistorialReservaResponse resultado =
                historialReservaService.obtenerPorId(id);

        // Then
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdHistorial());
        assertEquals(10L, resultado.getIdReserva());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaException() {

        // Given
        Long id = 99L;

        when(historialReservaRepository.findById(id))
                .thenReturn(Optional.empty());

        // When / Then
        assertThrows(
                HistorialReservaNoEncontradaException.class,
                () -> historialReservaService.obtenerPorId(id)
        );
    }

    @Test
    void eliminar_cuandoExiste_eliminaHistorial() {

        // Given
        Long id = 1L;

        when(historialReservaRepository.existsById(id))
                .thenReturn(true);

        // When
        historialReservaService.eliminar(id);

        // Then
        verify(historialReservaRepository)
                .deleteById(id);
    }

    @Test
    void eliminar_cuandoNoExiste_lanzaException() {

        // Given
        Long id = 50L;

        when(historialReservaRepository.existsById(id))
                .thenReturn(false);

        // When / Then
        assertThrows(
                HistorialReservaNoEncontradaException.class,
                () -> historialReservaService.eliminar(id)
        );

        verify(historialReservaRepository, never())
                .deleteById(id);
    }

    private HistorialReserva crearHistorial(Long id) {

        HistorialReserva historial = new HistorialReserva();

        historial.setIdHistorial(id);
        historial.setIdReserva(10L);
        historial.setEstadoAnterior(EstadoReserva.PENDIENTE);
        historial.setEstadoNuevo(EstadoReserva.CONFIRMADA);
        historial.setObservacion("Reserva confirmada");
        historial.setFechaCambio(LocalDateTime.now());

        return historial;
    }

    private HistorialReservaResponse crearResponse(Long id) {

        HistorialReservaResponse response =
                new HistorialReservaResponse();

        response.setIdHistorial(id);
        response.setIdReserva(10L);
        response.setEstadoAnterior(EstadoReserva.PENDIENTE);
        response.setEstadoNuevo(EstadoReserva.CONFIRMADA);
        response.setObservacion("Reserva confirmada");
        response.setFechaCambio(LocalDateTime.now());

        return response;
    }
}