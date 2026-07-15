package com.ReservaPro.ms_cancelacion.service;

import com.ReservaPro.ms_cancelacion.client.ReservaClient;
import com.ReservaPro.ms_cancelacion.dto.request.CancelacionRequest;
import com.ReservaPro.ms_cancelacion.dto.response.CancelacionResponse;
import com.ReservaPro.ms_cancelacion.exception.CancelacionNotFoundException;
import com.ReservaPro.ms_cancelacion.exception.ReglaNegocioException;
import com.ReservaPro.ms_cancelacion.mapper.CancelacionMapper;
import com.ReservaPro.ms_cancelacion.model.Cancelacion;
import com.ReservaPro.ms_cancelacion.model.EstadoReembolso;
import com.ReservaPro.ms_cancelacion.repository.CancelacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelacionServiceTests {

    @Mock
    private CancelacionRepository cancelacionRepository;

    @Mock
    private CancelacionMapper cancelacionMapper;

    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private CancelacionService cancelacionService;

    private Cancelacion cancelacion;
    private CancelacionRequest request;
    private CancelacionResponse response;

    @BeforeEach
    void prepararDatos() {

        cancelacion = crearCancelacion(1L);

        request = new CancelacionRequest();
        request.setMotivo("Cambio de planes");
        request.setFechaCancelacion(LocalDate.now());
        request.setEstadoReembolso(EstadoReembolso.PENDIENTE);
        request.setIdReserva(20L);

        response = crearResponse(1L);
    }

    // ---------- obtener() ----------

    @Test
    void obtenerDebeRetornarListaDeCancelaciones() {

        when(cancelacionRepository.findAll())
                .thenReturn(List.of(cancelacion));

        when(cancelacionMapper.toResponseList(List.of(cancelacion)))
                .thenReturn(List.of(response));

        List<CancelacionResponse> resultado = cancelacionService.obtener();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getIdCancelacion());

        verify(cancelacionRepository).findAll();
    }

    // ---------- obtenerPorId() ----------

    @Test
    void obtenerPorId_cuandoExiste_retornaCancelacion() {

        Long id = 1L;

        when(cancelacionRepository.findById(id))
                .thenReturn(Optional.of(cancelacion));

        when(cancelacionMapper.toResponse(cancelacion))
                .thenReturn(response);

        CancelacionResponse resultado = cancelacionService.obtenerPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdCancelacion());
        assertEquals("Cambio de planes", resultado.getMotivo());
        assertEquals(EstadoReembolso.PENDIENTE, resultado.getEstadoReembolso());

        verify(cancelacionRepository).findById(id);
        verify(cancelacionMapper).toResponse(cancelacion);
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaExcepcion() {

        Long id = 99L;

        when(cancelacionRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                CancelacionNotFoundException.class,
                () -> cancelacionService.obtenerPorId(id)
        );

        verify(cancelacionRepository).findById(id);
    }

    // ---------- crear() ----------

    @Test
    void crear_conDatosValidos_debeCrearCancelacion() {

        when(reservaClient.obtenerReservaPorId(20L))
                .thenReturn(new Object());

        when(cancelacionMapper.toEntity(request))
                .thenReturn(cancelacion);

        when(cancelacionRepository.save(cancelacion))
                .thenReturn(cancelacion);

        when(cancelacionMapper.toResponse(cancelacion))
                .thenReturn(response);

        CancelacionResponse resultado = cancelacionService.crear(request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdCancelacion());

        verify(reservaClient).obtenerReservaPorId(20L);
        verify(cancelacionRepository).save(cancelacion);
    }

    @Test
    void crear_conMotivoNulo_lanzaReglaNegocioException() {

        request.setMotivo(null);

        assertThrows(
                ReglaNegocioException.class,
                () -> cancelacionService.crear(request)
        );

        verify(cancelacionRepository, never()).save(any(Cancelacion.class));
        verify(reservaClient, never()).obtenerReservaPorId(anyLong());
    }

    @Test
    void crear_conMotivoVacio_lanzaReglaNegocioException() {

        request.setMotivo("   ");

        assertThrows(
                ReglaNegocioException.class,
                () -> cancelacionService.crear(request)
        );

        verify(cancelacionRepository, never()).save(any(Cancelacion.class));
    }

    @Test
    void crear_conFechaNula_lanzaReglaNegocioException() {

        request.setFechaCancelacion(null);

        assertThrows(
                ReglaNegocioException.class,
                () -> cancelacionService.crear(request)
        );

        verify(cancelacionRepository, never()).save(any(Cancelacion.class));
    }

    @Test
    void crear_conFechaFutura_lanzaReglaNegocioException() {

        request.setFechaCancelacion(LocalDate.now().plusDays(5));

        assertThrows(
                ReglaNegocioException.class,
                () -> cancelacionService.crear(request)
        );

        verify(cancelacionRepository, never()).save(any(Cancelacion.class));
    }

    @Test
    void crear_conIdReservaNulo_lanzaReglaNegocioException() {

        request.setIdReserva(null);

        assertThrows(
                ReglaNegocioException.class,
                () -> cancelacionService.crear(request)
        );

        verify(cancelacionRepository, never()).save(any(Cancelacion.class));
    }

    // ---------- actualizar() ----------

    @Test
    void actualizar_conDatosValidos_debeActualizarCancelacion() {

        Long id = 1L;

        request.setMotivo("Nuevo motivo");

        when(cancelacionRepository.findById(id))
                .thenReturn(Optional.of(cancelacion));

        when(reservaClient.obtenerReservaPorId(20L))
                .thenReturn(new Object());

        when(cancelacionRepository.save(cancelacion))
                .thenReturn(cancelacion);

        when(cancelacionMapper.toResponse(cancelacion))
                .thenReturn(response);

        CancelacionResponse resultado = cancelacionService.actualizar(id, request);

        assertNotNull(resultado);
        assertEquals("Nuevo motivo", cancelacion.getMotivo());

        verify(cancelacionRepository).findById(id);
        verify(cancelacionRepository).save(cancelacion);
    }

    @Test
    void actualizar_cuandoNoExiste_lanzaExcepcion() {

        Long id = 99L;

        when(cancelacionRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                CancelacionNotFoundException.class,
                () -> cancelacionService.actualizar(id, request)
        );

        verify(cancelacionRepository, never()).save(any(Cancelacion.class));
        verify(reservaClient, never()).obtenerReservaPorId(anyLong());
    }

    @Test
    void actualizar_conMotivoInvalido_lanzaReglaNegocioException() {

        Long id = 1L;

        request.setMotivo("");

        when(cancelacionRepository.findById(id))
                .thenReturn(Optional.of(cancelacion));

        assertThrows(
                ReglaNegocioException.class,
                () -> cancelacionService.actualizar(id, request)
        );

        verify(cancelacionRepository, never()).save(any(Cancelacion.class));
    }

    // ---------- eliminar() ----------

    @Test
    void eliminar_cuandoExiste_eliminaCancelacion() {

        Long id = 1L;

        when(cancelacionRepository.findById(id))
                .thenReturn(Optional.of(cancelacion));

        cancelacionService.eliminar(id);

        verify(cancelacionRepository).findById(id);
        verify(cancelacionRepository).delete(cancelacion);
    }

    @Test
    void eliminar_cuandoNoExiste_lanzaExcepcion() {

        Long id = 50L;

        when(cancelacionRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                CancelacionNotFoundException.class,
                () -> cancelacionService.eliminar(id)
        );

        verify(cancelacionRepository, never()).delete(any(Cancelacion.class));
    }

    // ---------- obtenerPorReserva() ----------

    @Test
    void obtenerPorReserva_debeRetornarLista() {

        when(cancelacionRepository.findByIdReserva(20L))
                .thenReturn(List.of(cancelacion));

        when(cancelacionMapper.toResponseList(List.of(cancelacion)))
                .thenReturn(List.of(response));

        List<CancelacionResponse> resultado = cancelacionService.obtenerPorReserva(20L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(cancelacionRepository).findByIdReserva(20L);
    }

    // ---------- obtenerPorEstado() ----------

    @Test
    void obtenerPorEstado_debeRetornarLista() {

        when(cancelacionRepository.findByEstadoReembolso(EstadoReembolso.PENDIENTE))
                .thenReturn(List.of(cancelacion));

        when(cancelacionMapper.toResponseList(List.of(cancelacion)))
                .thenReturn(List.of(response));

        List<CancelacionResponse> resultado =
                cancelacionService.obtenerPorEstado(EstadoReembolso.PENDIENTE);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(cancelacionRepository).findByEstadoReembolso(EstadoReembolso.PENDIENTE);
    }

    // ---------- obtenerPorFecha() ----------

    @Test
    void obtenerPorFecha_debeRetornarLista() {

        LocalDate fecha = LocalDate.now();

        when(cancelacionRepository.findByFechaCancelacion(fecha))
                .thenReturn(List.of(cancelacion));

        when(cancelacionMapper.toResponseList(List.of(cancelacion)))
                .thenReturn(List.of(response));

        List<CancelacionResponse> resultado = cancelacionService.obtenerPorFecha(fecha);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        verify(cancelacionRepository).findByFechaCancelacion(fecha);
    }

    // ---------- helpers ----------

    private Cancelacion crearCancelacion(Long id) {

        Cancelacion c = new Cancelacion();

        c.setIdCancelacion(id);
        c.setMotivo("Cambio de planes");
        c.setFechaCancelacion(LocalDate.now());
        c.setEstadoReembolso(EstadoReembolso.PENDIENTE);
        c.setIdReserva(20L);

        return c;
    }

    private CancelacionResponse crearResponse(Long id) {

        CancelacionResponse r = new CancelacionResponse();

        r.setIdCancelacion(id);
        r.setMotivo("Cambio de planes");
        r.setFechaCancelacion(LocalDate.now());
        r.setEstadoReembolso(EstadoReembolso.PENDIENTE);
        r.setIdReserva(20L);

        return r;
    }
}
