package com.ReservaPro.ms_calificacion.service;

import com.ReservaPro.ms_calificacion.dto.request.CalificacionRequest;
import com.ReservaPro.ms_calificacion.dto.response.CalificacionResponse;
import com.ReservaPro.ms_calificacion.enums.EstadoCalificacion;
import com.ReservaPro.ms_calificacion.exception.CalificacionNoEncontradaException;
import com.ReservaPro.ms_calificacion.mapper.CalificacionMapper;
import com.ReservaPro.ms_calificacion.model.Calificacion;
import com.ReservaPro.ms_calificacion.repository.CalificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalificacionServiceTests {

    @Mock
    private CalificacionRepository calificacionRepository;

    @Mock
    private CalificacionMapper calificacionMapper;

    @Mock
    private DiscoveryClient discoveryClient;

    @InjectMocks
    private CalificacionService calificacionService;

    private Calificacion calificacion;
    private CalificacionRequest request;
    private CalificacionResponse response;

    @BeforeEach
    void prepararDatos() {

        when(discoveryClient.getInstances("ms-calificacion"))
                .thenReturn(Collections.emptyList());

        calificacion = Calificacion.builder()
                .id(1L)
                .idReserva(20L)
                .idUsuario(10L)
                .puntuacion(5)
                .comentario("Excelente servicio")
                .estado(EstadoCalificacion.PENDIENTE)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();

        request = new CalificacionRequest();
        request.setIdReserva(20L);
        request.setIdUsuario(10L);
        request.setPuntuacion(5);
        request.setComentario("Excelente servicio");

        response = new CalificacionResponse();
        response.setId(1L);
        response.setIdReserva(20L);
        response.setIdUsuario(10L);
        response.setPuntuacion(5);
        response.setComentario("Excelente servicio");
        response.setEstado(EstadoCalificacion.PENDIENTE);
        response.setFechaCreacion(calificacion.getFechaCreacion());
        response.setFechaActualizacion(calificacion.getFechaActualizacion());
    }

    @Test
    void obtenerTodasDebeRetornarLista() {

        when(calificacionRepository.findAll())
                .thenReturn(List.of(calificacion));

        when(calificacionMapper.toResponseList(List.of(calificacion)))
                .thenReturn(List.of(response));

        List<CalificacionResponse> resultado =
                calificacionService.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals(5, resultado.get(0).getPuntuacion());

        verify(calificacionRepository).findAll();
        verify(calificacionMapper)
                .toResponseList(List.of(calificacion));
    }

    @Test
    void obtenerPorIdDebeRetornarCalificacion() {

        when(calificacionRepository.findById(1L))
                .thenReturn(Optional.of(calificacion));

        when(calificacionMapper.toResponse(calificacion))
                .thenReturn(response);

        CalificacionResponse resultado =
                calificacionService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(20L, resultado.getIdReserva());
        assertEquals(10L, resultado.getIdUsuario());

        verify(calificacionRepository).findById(1L);
        verify(calificacionMapper).toResponse(calificacion);
    }

    @Test
    void obtenerPorIdInexistenteDebeLanzarExcepcion() {

        when(calificacionRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                CalificacionNoEncontradaException.class,
                () -> calificacionService.obtenerPorId(99L)
        );

        verify(calificacionRepository).findById(99L);
    }

    @Test
    void obtenerPorUsuarioDebeRetornarLista() {

        when(calificacionRepository.findByIdUsuario(10L))
                .thenReturn(List.of(calificacion));

        when(calificacionMapper.toResponseList(List.of(calificacion)))
                .thenReturn(List.of(response));

        List<CalificacionResponse> resultado =
                calificacionService.obtenerPorUsuario(10L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getIdUsuario());

        verify(calificacionRepository).findByIdUsuario(10L);
    }

    @Test
    void obtenerPorReservaDebeRetornarLista() {

        when(calificacionRepository.findByIdReserva(20L))
                .thenReturn(List.of(calificacion));

        when(calificacionMapper.toResponseList(List.of(calificacion)))
                .thenReturn(List.of(response));

        List<CalificacionResponse> resultado =
                calificacionService.obtenerPorReserva(20L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(20L, resultado.get(0).getIdReserva());

        verify(calificacionRepository).findByIdReserva(20L);
    }

    @Test
    void crearDebeGuardarCalificacion() {

        when(calificacionRepository
                .existsByIdReservaAndIdUsuario(20L, 10L))
                .thenReturn(false);

        when(calificacionMapper.toEntity(request))
                .thenReturn(calificacion);

        when(calificacionRepository.save(calificacion))
                .thenReturn(calificacion);

        when(calificacionMapper.toResponse(calificacion))
                .thenReturn(response);

        CalificacionResponse resultado =
                calificacionService.crear(request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(5, resultado.getPuntuacion());

        verify(calificacionRepository)
                .existsByIdReservaAndIdUsuario(20L, 10L);

        verify(calificacionMapper).toEntity(request);
        verify(calificacionRepository).save(calificacion);
    }

    @Test
    void crearDuplicadaDebeLanzarExcepcion() {

        when(calificacionRepository
                .existsByIdReservaAndIdUsuario(20L, 10L))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> calificacionService.crear(request)
        );

        verify(calificacionRepository, never())
                .save(any(Calificacion.class));
    }

    @Test
    void actualizarPendienteDebeActualizarCalificacion() {

        request.setPuntuacion(4);
        request.setComentario("Buen servicio");

        response.setPuntuacion(4);
        response.setComentario("Buen servicio");

        when(calificacionRepository.findById(1L))
                .thenReturn(Optional.of(calificacion));

        when(calificacionRepository.save(calificacion))
                .thenReturn(calificacion);

        when(calificacionMapper.toResponse(calificacion))
                .thenReturn(response);

        CalificacionResponse resultado =
                calificacionService.actualizar(1L, request);

        assertNotNull(resultado);
        assertEquals(4, resultado.getPuntuacion());
        assertEquals("Buen servicio", resultado.getComentario());

        assertEquals(4, calificacion.getPuntuacion());
        assertEquals("Buen servicio", calificacion.getComentario());

        verify(calificacionRepository).findById(1L);
        verify(calificacionRepository).save(calificacion);
    }

    @Test
    void actualizarPublicadaDebeLanzarExcepcion() {

        calificacion.setEstado(EstadoCalificacion.PUBLICADA);

        when(calificacionRepository.findById(1L))
                .thenReturn(Optional.of(calificacion));

        assertThrows(
                IllegalStateException.class,
                () -> calificacionService.actualizar(1L, request)
        );

        verify(calificacionRepository, never())
                .save(any(Calificacion.class));
    }

    @Test
    void eliminarDebeEliminarCalificacion() {

        when(calificacionRepository.findById(1L))
                .thenReturn(Optional.of(calificacion));

        calificacionService.eliminar(1L);

        verify(calificacionRepository).findById(1L);
        verify(calificacionRepository).delete(calificacion);
    }

    @Test
    void eliminarInexistenteDebeLanzarExcepcion() {

        when(calificacionRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                CalificacionNoEncontradaException.class,
                () -> calificacionService.eliminar(99L)
        );

        verify(calificacionRepository, never())
                .delete(any(Calificacion.class));
    }

    @Test
    void publicarPendienteDebeCambiarEstado() {

        response.setEstado(EstadoCalificacion.PUBLICADA);

        when(calificacionRepository.findById(1L))
                .thenReturn(Optional.of(calificacion));

        when(calificacionRepository.save(calificacion))
                .thenReturn(calificacion);

        when(calificacionMapper.toResponse(calificacion))
                .thenReturn(response);

        CalificacionResponse resultado =
                calificacionService.publicar(1L);

        assertNotNull(resultado);

        assertEquals(
                EstadoCalificacion.PUBLICADA,
                calificacion.getEstado()
        );

        assertEquals(
                EstadoCalificacion.PUBLICADA,
                resultado.getEstado()
        );

        verify(calificacionRepository).save(calificacion);
    }

    @Test
    void publicarNoPendienteDebeLanzarExcepcion() {

        calificacion.setEstado(EstadoCalificacion.PUBLICADA);

        when(calificacionRepository.findById(1L))
                .thenReturn(Optional.of(calificacion));

        assertThrows(
                IllegalStateException.class,
                () -> calificacionService.publicar(1L)
        );

        verify(calificacionRepository, never())
                .save(any(Calificacion.class));
    }

    @Test
    void moderarDebeCambiarEstado() {

        response.setEstado(EstadoCalificacion.PUBLICADA);

        when(calificacionRepository.findById(1L))
                .thenReturn(Optional.of(calificacion));

        when(calificacionRepository.save(calificacion))
                .thenReturn(calificacion);

        when(calificacionMapper.toResponse(calificacion))
                .thenReturn(response);

        CalificacionResponse resultado =
                calificacionService.moderar(
                        1L,
                        EstadoCalificacion.PUBLICADA
                );

        assertNotNull(resultado);

        assertEquals(
                EstadoCalificacion.PUBLICADA,
                calificacion.getEstado()
        );

        verify(calificacionRepository).save(calificacion);
    }

    @Test
    void obtenerPromedioDebeRetornarPromedio() {

        Calificacion calificacion1 =
                Calificacion.builder()
                        .puntuacion(5)
                        .estado(EstadoCalificacion.PUBLICADA)
                        .build();

        Calificacion calificacion2 =
                Calificacion.builder()
                        .puntuacion(4)
                        .estado(EstadoCalificacion.PUBLICADA)
                        .build();

        Calificacion calificacion3 =
                Calificacion.builder()
                        .puntuacion(3)
                        .estado(EstadoCalificacion.PUBLICADA)
                        .build();

        when(calificacionRepository.findByEstado(
                EstadoCalificacion.PUBLICADA
        )).thenReturn(
                List.of(
                        calificacion1,
                        calificacion2,
                        calificacion3
                )
        );

        Double resultado =
                calificacionService.obtenerPromedioCalificaciones();

        assertEquals(4.0, resultado);
    }

    @Test
    void obtenerPromedioSinCalificacionesDebeRetornarCero() {

        when(calificacionRepository.findByEstado(
                EstadoCalificacion.PUBLICADA
        )).thenReturn(Collections.emptyList());

        Double resultado =
                calificacionService.obtenerPromedioCalificaciones();

        assertEquals(0.0, resultado);
    }
}