package com.ReservaPro.ms_cancelacion.controller;

import com.ReservaPro.ms_cancelacion.dto.request.CancelacionRequest;
import com.ReservaPro.ms_cancelacion.dto.response.CancelacionResponse;
import com.ReservaPro.ms_cancelacion.model.EstadoReembolso;
import com.ReservaPro.ms_cancelacion.service.CancelacionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelacionControllerTests {

    @Mock
    private CancelacionService cancelacionService;

    @InjectMocks
    private CancelacionController cancelacionController;

    private CancelacionResponse response;
    private CancelacionRequest request;

    @BeforeEach
    void setUp() {
        response = new CancelacionResponse();
        response.setIdCancelacion(1L);
        response.setMotivo("Cambio de planes");
        response.setFechaCancelacion(LocalDate.now());
        response.setEstadoReembolso(EstadoReembolso.PENDIENTE);
        response.setIdReserva(20L);

        request = new CancelacionRequest();
        request.setMotivo("Cambio de planes");
        request.setFechaCancelacion(LocalDate.now());
        request.setEstadoReembolso(EstadoReembolso.PENDIENTE);
        request.setIdReserva(20L);
    }

    @Test
    void obtenerCancelaciones_debeRetornar200() {

        when(cancelacionService.obtener()).thenReturn(List.of(response));

        ResponseEntity<List<CancelacionResponse>> resultado =
                cancelacionController.obtenerCancelaciones();

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
        verify(cancelacionService).obtener();
    }

    @Test
    void obtenerCancelacion_debeRetornar200() {

        when(cancelacionService.obtenerPorId(1L)).thenReturn(response);

        ResponseEntity<CancelacionResponse> resultado =
                cancelacionController.obtenerCancelacion(1L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1L, resultado.getBody().getIdCancelacion());
    }

    @Test
    void obtenerPorReserva_debeRetornar200() {

        when(cancelacionService.obtenerPorReserva(20L)).thenReturn(List.of(response));

        ResponseEntity<List<CancelacionResponse>> resultado =
                cancelacionController.obtenerPorReserva(20L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
    }

    @Test
    void obtenerPorEstado_debeRetornar200() {

        when(cancelacionService.obtenerPorEstado(EstadoReembolso.PENDIENTE))
                .thenReturn(List.of(response));

        ResponseEntity<List<CancelacionResponse>> resultado =
                cancelacionController.obtenerPorEstado(EstadoReembolso.PENDIENTE);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
    }

    @Test
    void obtenerPorFecha_debeRetornar200() {

        LocalDate fecha = LocalDate.now();

        when(cancelacionService.obtenerPorFecha(fecha)).thenReturn(List.of(response));

        ResponseEntity<List<CancelacionResponse>> resultado =
                cancelacionController.obtenerPorFecha(fecha);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
    }

    @Test
    void crearCancelacion_debeRetornar201() {

        when(cancelacionService.crear(request)).thenReturn(response);

        ResponseEntity<CancelacionResponse> resultado =
                cancelacionController.crearCancelacion(request);

        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertEquals(1L, resultado.getBody().getIdCancelacion());
    }

    @Test
    void actualizarCancelacion_debeRetornar200() {

        when(cancelacionService.actualizar(1L, request)).thenReturn(response);

        ResponseEntity<CancelacionResponse> resultado =
                cancelacionController.actualizarCancelacion(1L, request);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
    }

    @Test
    void eliminarCancelacion_debeRetornar204() {

        ResponseEntity<Void> resultado = cancelacionController.eliminarCancelacion(1L);

        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
        verify(cancelacionService).eliminar(1L);
    }
}
