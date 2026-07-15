package com.ReservaPro.ms_reserva.controller;

import com.ReservaPro.ms_reserva.dto.request.ReservaPagoRequest;
import com.ReservaPro.ms_reserva.dto.request.ReservaRequest;
import com.ReservaPro.ms_reserva.dto.response.ReservaCompletaResponse;
import com.ReservaPro.ms_reserva.dto.response.ReservaResponse;
import com.ReservaPro.ms_reserva.enums.EstadoReserva;
import com.ReservaPro.ms_reserva.service.ReservaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaControllerTest {

    @Mock
    private ReservaService reservaService;

    private ReservaController controller;

    @BeforeEach
    void setUp() {
        controller = new ReservaController(reservaService);
    }

    private ReservaResponse crearResponse(Long id) {
        ReservaResponse response = new ReservaResponse();
        response.setId(id);
        response.setIdUsuario(10L);
        response.setEstadoReserva(EstadoReserva.PENDIENTE_PAGO.getValor());
        return response;
    }

    @Test
    void obtenerTodos_retornaOk() {
        List<ReservaResponse> lista = List.of(crearResponse(1L));
        when(reservaService.obtenerTodos()).thenReturn(lista);

        ResponseEntity<List<ReservaResponse>> resultado = controller.obtenerTodos();

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
    }

    @Test
    void obtenerPorId_retornaOk() {
        ReservaResponse response = crearResponse(1L);
        when(reservaService.obtenerPorId(1L)).thenReturn(response);

        ResponseEntity<ReservaResponse> resultado = controller.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1L, resultado.getBody().getId());
    }

    @Test
    void obtenerReservaCompleta_retornaOk() {
        ReservaCompletaResponse response = new ReservaCompletaResponse();
        response.setReserva(crearResponse(1L));
        when(reservaService.obtenerReservaCompleta(1L)).thenReturn(response);

        ResponseEntity<ReservaCompletaResponse> resultado = controller.obtenerReservaCompleta(1L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertNotNull(resultado.getBody().getReserva());
    }

    @Test
    void crear_retornaCreated() {
        ReservaRequest request = mock(ReservaRequest.class);
        ReservaResponse response = crearResponse(1L);
        when(reservaService.crear(request)).thenReturn(response);

        ResponseEntity<ReservaResponse> resultado = controller.crear(request);

        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertEquals(1L, resultado.getBody().getId());
    }

    @Test
    void actualizar_retornaOk() {
        ReservaRequest request = mock(ReservaRequest.class);
        ReservaResponse response = crearResponse(1L);
        when(reservaService.actualizar(1L, request)).thenReturn(response);

        ResponseEntity<ReservaResponse> resultado = controller.actualizar(1L, request);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
    }

    @Test
    void eliminar_retornaNoContent() {
        doNothing().when(reservaService).eliminar(1L);

        ResponseEntity<Void> resultado = controller.eliminar(1L);

        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
        verify(reservaService).eliminar(1L);
    }

    @Test
    void confirmarPago_retornaOk() {
        ReservaPagoRequest request = new ReservaPagoRequest();
        request.setIdPago(7L);
        ReservaResponse response = crearResponse(1L);
        when(reservaService.confirmarPago(1L, request)).thenReturn(response);

        ResponseEntity<ReservaResponse> resultado = controller.confirmarPago(1L, request);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
    }

    @Test
    void confirmarReserva_retornaOk() {
        ReservaResponse response = crearResponse(1L);
        when(reservaService.confirmarReserva(1L)).thenReturn(response);

        ResponseEntity<ReservaResponse> resultado = controller.confirmarReserva(1L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
    }

    @Test
    void cancelarReserva_retornaOk() {
        ReservaResponse response = crearResponse(1L);
        when(reservaService.cancelarReserva(1L)).thenReturn(response);

        ResponseEntity<ReservaResponse> resultado = controller.cancelarReserva(1L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
    }

    @Test
    void completarReserva_retornaOk() {
        ReservaResponse response = crearResponse(1L);
        when(reservaService.completarReserva(1L)).thenReturn(response);

        ResponseEntity<ReservaResponse> resultado = controller.completarReserva(1L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
    }

    @Test
    void obtenerPorUsuario_retornaOk() {
        List<ReservaResponse> lista = List.of(crearResponse(1L));
        when(reservaService.obtenerPorUsuario(10L)).thenReturn(lista);

        ResponseEntity<List<ReservaResponse>> resultado = controller.obtenerPorUsuario(10L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
    }

    @Test
    void obtenerPorEstado_retornaOk() {
        List<ReservaResponse> lista = List.of(crearResponse(1L));
        when(reservaService.obtenerPorEstado(EstadoReserva.PENDIENTE_PAGO)).thenReturn(lista);

        ResponseEntity<List<ReservaResponse>> resultado = controller.obtenerPorEstado(EstadoReserva.PENDIENTE_PAGO);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
    }

    @Test
    void obtenerPorUsuarioYEstado_retornaOk() {
        List<ReservaResponse> lista = List.of(crearResponse(1L));
        when(reservaService.obtenerPorUsuarioYEstado(10L, EstadoReserva.PENDIENTE_PAGO)).thenReturn(lista);

        ResponseEntity<List<ReservaResponse>> resultado =
                controller.obtenerPorUsuarioYEstado(10L, EstadoReserva.PENDIENTE_PAGO);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
    }
}
