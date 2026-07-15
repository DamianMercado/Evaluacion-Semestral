package com.ReservaPro.ms_gestion_servicio.controller;

import com.ReservaPro.ms_gestion_servicio.dto.request.GestionServicioRequest;
import com.ReservaPro.ms_gestion_servicio.dto.response.GestionServicioResponse;
import com.ReservaPro.ms_gestion_servicio.enums.EstadoServicio;
import com.ReservaPro.ms_gestion_servicio.service.GestionServicioService;

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
class GestionServicioControllerTest {

    @Mock
    private GestionServicioService gestionServicioService;

    private GestionServicioController controller;

    @BeforeEach
    void setUp() {
        controller = new GestionServicioController(gestionServicioService);
    }

    private GestionServicioResponse crearResponse(Long id) {
        GestionServicioResponse response = new GestionServicioResponse();
        response.setId(id);
        response.setNombre("Arriendo cancha de futbol");
        response.setPrecioServicio(49.99);
        response.setDuracionMinuto(60);
        response.setEstadoServicio(EstadoServicio.ACTIVADO.getValor());
        response.setUbicacion("Sucursal Centro");
        response.setCapacidad(10);
        response.setCondiciones("Se requiere pago anticipado");
        response.setProveedorId(42L);
        return response;
    }

    @Test
    void obtenerTodos_retornaOkConLista() {
        List<GestionServicioResponse> lista = List.of(crearResponse(1L));
        when(gestionServicioService.obtenerTodos()).thenReturn(lista);

        ResponseEntity<List<GestionServicioResponse>> resultado = controller.obtenerTodos();

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
        verify(gestionServicioService).obtenerTodos();
    }

    @Test
    void obtenerPorId_retornaOkConServicio() {
        GestionServicioResponse response = crearResponse(1L);
        when(gestionServicioService.obtenerPorId(1L)).thenReturn(response);

        ResponseEntity<GestionServicioResponse> resultado = controller.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1L, resultado.getBody().getId());
        verify(gestionServicioService).obtenerPorId(1L);
    }

    @Test
    void obtenerPorEstado_retornaOkConLista() {
        List<GestionServicioResponse> lista = List.of(crearResponse(1L));
        when(gestionServicioService.obtenerPorEstado(EstadoServicio.ACTIVADO)).thenReturn(lista);

        ResponseEntity<List<GestionServicioResponse>> resultado =
                controller.obtenerPorEstado(EstadoServicio.ACTIVADO);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
        verify(gestionServicioService).obtenerPorEstado(EstadoServicio.ACTIVADO);
    }

    @Test
    void buscarPorNombre_retornaOkConLista() {
        List<GestionServicioResponse> lista = List.of(crearResponse(1L));
        when(gestionServicioService.buscarPorNombre("cancha")).thenReturn(lista);

        ResponseEntity<List<GestionServicioResponse>> resultado = controller.buscarPorNombre("cancha");

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
        verify(gestionServicioService).buscarPorNombre("cancha");
    }

    @Test
    void obtenerPorProveedor_retornaOkConLista() {
        List<GestionServicioResponse> lista = List.of(crearResponse(1L));
        when(gestionServicioService.obtenerPorProveedor(42L)).thenReturn(lista);

        ResponseEntity<List<GestionServicioResponse>> resultado = controller.obtenerPorProveedor(42L);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1, resultado.getBody().size());
        verify(gestionServicioService).obtenerPorProveedor(42L);
    }

    @Test
    void crear_retornaCreated() {
        GestionServicioRequest request = mock(GestionServicioRequest.class);
        GestionServicioResponse response = crearResponse(1L);
        when(gestionServicioService.crear(request)).thenReturn(response);

        ResponseEntity<GestionServicioResponse> resultado = controller.crear(request);

        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertEquals(1L, resultado.getBody().getId());
        verify(gestionServicioService).crear(request);
    }

    @Test
    void actualizar_retornaOk() {
        GestionServicioRequest request = mock(GestionServicioRequest.class);
        GestionServicioResponse response = crearResponse(1L);
        when(gestionServicioService.actualizar(1L, request)).thenReturn(response);

        ResponseEntity<GestionServicioResponse> resultado = controller.actualizar(1L, request);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(1L, resultado.getBody().getId());
        verify(gestionServicioService).actualizar(1L, request);
    }

    @Test
    void eliminar_retornaNoContent() {
        doNothing().when(gestionServicioService).eliminar(1L);

        ResponseEntity<Void> resultado = controller.eliminar(1L);

        assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
        verify(gestionServicioService).eliminar(1L);
    }

    @Test
    void cambiarEstado_retornaOk() {
        GestionServicioResponse response = crearResponse(1L);
        response.setEstadoServicio(EstadoServicio.MANTENIMIENTO.getValor());
        when(gestionServicioService.cambiarEstado(1L, EstadoServicio.MANTENIMIENTO)).thenReturn(response);

        ResponseEntity<GestionServicioResponse> resultado =
                controller.cambiarEstado(1L, EstadoServicio.MANTENIMIENTO);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(EstadoServicio.MANTENIMIENTO.getValor(), resultado.getBody().getEstadoServicio());
        verify(gestionServicioService).cambiarEstado(1L, EstadoServicio.MANTENIMIENTO);
    }
}
