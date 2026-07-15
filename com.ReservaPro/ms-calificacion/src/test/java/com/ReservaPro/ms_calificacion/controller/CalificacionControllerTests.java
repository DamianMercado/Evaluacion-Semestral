package com.ReservaPro.ms_calificacion.controller;

import com.ReservaPro.ms_calificacion.dto.request.CalificacionRequest;
import com.ReservaPro.ms_calificacion.dto.response.CalificacionResponse;
import com.ReservaPro.ms_calificacion.enums.EstadoCalificacion;
import com.ReservaPro.ms_calificacion.service.CalificacionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalificacionControllerTests {

    @Mock
    private CalificacionService calificacionService;

    @InjectMocks
    private CalificacionController calificacionController;

    private CalificacionResponse response;
    private CalificacionRequest request;

    @BeforeEach
    void setUp() {
        response = new CalificacionResponse();
        response.setId(1L);
        response.setIdReserva(20L);
        response.setIdUsuario(10L);
        response.setPuntuacion(5);
        response.setComentario("Excelente");
        response.setEstado(EstadoCalificacion.PENDIENTE);

        request = new CalificacionRequest();
        request.setIdReserva(20L);
        request.setIdUsuario(10L);
        request.setPuntuacion(5);
        request.setComentario("Excelente");
    }

    @Test
    void obtenerTodas_debeRetornar200ConLista() {

        when(calificacionService.obtenerTodas()).thenReturn(List.of(response));

        ResponseEntity<List<CalificacionResponse>> resultado =
                calificacionController.obtenerTodas();

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
        verify(calificacionService).obtenerTodas();
    }

    @Test
    void obtenerPorId_debeRetornar200() {

        when(calificacionService.obtenerPorId(1L)).thenReturn(response);

        ResponseEntity<CalificacionResponse> resultado =
                calificacionController.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1L, resultado.getBody().getId());
    }

    @Test
    void obtenerPorUsuario_debeRetornar200() {

        when(calificacionService.obtenerPorUsuario(10L)).thenReturn(List.of(response));

        ResponseEntity<List<CalificacionResponse>> resultado =
                calificacionController.obtenerPorUsuario(10L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
    }

    @Test
    void obtenerPorReserva_debeRetornar200() {

        when(calificacionService.obtenerPorReserva(20L)).thenReturn(List.of(response));

        ResponseEntity<List<CalificacionResponse>> resultado =
                calificacionController.obtenerPorReserva(20L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
    }

    @Test
    void obtenerPromedio_debeRetornar200() {

        when(calificacionService.obtenerPromedioCalificaciones()).thenReturn(4.5);

        ResponseEntity<Double> resultado = calificacionController.obtenerPromedio();

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(4.5, resultado.getBody());
    }

    @Test
    void crear_debeRetornar201() {

        when(calificacionService.crear(request)).thenReturn(response);

        ResponseEntity<CalificacionResponse> resultado =
                calificacionController.crear(request);

        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertEquals(1L, resultado.getBody().getId());
    }

    @Test
    void actualizar_debeRetornar200() {

        when(calificacionService.actualizar(1L, request)).thenReturn(response);

        ResponseEntity<CalificacionResponse> resultado =
                calificacionController.actualizar(1L, request);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
    }

    @Test
    void eliminar_debeRetornar204() {

        ResponseEntity<Void> resultado = calificacionController.eliminar(1L);

        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
        verify(calificacionService).eliminar(1L);
    }

    @Test
    void publicar_debeRetornar200() {

        response.setEstado(EstadoCalificacion.PUBLICADA);
        when(calificacionService.publicar(1L)).thenReturn(response);

        ResponseEntity<CalificacionResponse> resultado =
                calificacionController.publicar(1L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(EstadoCalificacion.PUBLICADA, resultado.getBody().getEstado());
    }

    @Test
    void moderar_debeRetornar200() {

        response.setEstado(EstadoCalificacion.MODERADA);
        when(calificacionService.moderar(1L, EstadoCalificacion.MODERADA)).thenReturn(response);

        ResponseEntity<CalificacionResponse> resultado =
                calificacionController.moderar(1L, EstadoCalificacion.MODERADA);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(EstadoCalificacion.MODERADA, resultado.getBody().getEstado());
    }
}
