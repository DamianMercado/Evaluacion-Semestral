package com.ReservaPro.ms_disponibilidad.service;

import com.ReservaPro.ms_disponibilidad.dto.response.DisponibilidadResponse;
import com.ReservaPro.ms_disponibilidad.exception.DisponibilidadNotFoundException;
import com.ReservaPro.ms_disponibilidad.mapper.DisponibilidadMapper;
import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;
import com.ReservaPro.ms_disponibilidad.model.EstadoDisponibilidad;
import com.ReservaPro.ms_disponibilidad.repository.DisponibilidadRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisponibilidadServiceTests {

    @Mock
    private DisponibilidadRepository disponibilidadRepository;

    @Mock
    private DisponibilidadMapper disponibilidadMapper;

    @InjectMocks
    private DisponibilidadService disponibilidadService;

    @Test
    void obtenerPorId_cuandoExiste_retornaDisponibilidad() {

        Long id = 1L;
        Disponibilidad disponibilidad = crearDisponibilidad(id);
        DisponibilidadResponse response = crearResponse(id);

        when(disponibilidadRepository.findById(id))
                .thenReturn(Optional.of(disponibilidad));

        when(disponibilidadMapper.toResponse(disponibilidad))
                .thenReturn(response);

        DisponibilidadResponse resultado =
                disponibilidadService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdDisponibilidad());
        assertEquals(
                EstadoDisponibilidad.DISPONIBLE,
                resultado.getEstado()
        );
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaException() {

        Long id = 99L;

        when(disponibilidadRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                DisponibilidadNotFoundException.class,
                () -> disponibilidadService.obtenerPorId(id)
        );
    }

    @Test
    void eliminar_cuandoExiste_eliminaDisponibilidad() {

        Long id = 1L;

        when(disponibilidadRepository.existsById(id))
                .thenReturn(true);

        disponibilidadService.eliminar(id);

        verify(disponibilidadRepository)
                .deleteById(id);
    }

    @Test
    void eliminar_cuandoNoExiste_lanzaException() {

        Long id = 50L;

        when(disponibilidadRepository.existsById(id))
                .thenReturn(false);

        assertThrows(
                DisponibilidadNotFoundException.class,
                () -> disponibilidadService.eliminar(id)
        );

        verify(disponibilidadRepository, never())
                .deleteById(id);
    }

    private Disponibilidad crearDisponibilidad(Long id) {

        Disponibilidad disponibilidad =
                new Disponibilidad();

        disponibilidad.setIdDisponibilidad(id);
        disponibilidad.setFecha(LocalDate.now());
        disponibilidad.setHoraInicio(LocalTime.of(9, 0));
        disponibilidad.setHoraFin(LocalTime.of(18, 0));
        disponibilidad.setCuposDisponibles(5);
        disponibilidad.setCuposTotales(10);
        disponibilidad.setEstado(
                EstadoDisponibilidad.DISPONIBLE
        );

        return disponibilidad;
    }

    private DisponibilidadResponse crearResponse(Long id) {

        DisponibilidadResponse response =
                new DisponibilidadResponse();

        response.setIdDisponibilidad(id);
        response.setFecha(LocalDate.now());
        response.setHoraInicio(LocalTime.of(9, 0));
        response.setHoraFin(LocalTime.of(18, 0));
        response.setCuposDisponibles(5);
        response.setCuposTotales(10);
        response.setEstado(
                EstadoDisponibilidad.DISPONIBLE
        );

        return response;
    }
}