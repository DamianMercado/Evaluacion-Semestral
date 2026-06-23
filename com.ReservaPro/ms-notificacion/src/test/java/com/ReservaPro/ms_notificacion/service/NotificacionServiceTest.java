package com.ReservaPro.ms_notificacion.service;

import com.ReservaPro.ms_notificacion.dto.response.NotificacionResponse;
import com.ReservaPro.ms_notificacion.exception.NotificacionNoEncontradaException;
import com.ReservaPro.ms_notificacion.mapper.NotificacionMapper;
import com.ReservaPro.ms_notificacion.model.Notificacion;
import com.ReservaPro.ms_notificacion.repository.NotificacionRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private NotificacionMapper notificacionMapper;

    @InjectMocks
    private NotificacionService notificacionService;

    @Test
    void obtenerPorId_cuandoExiste_retornaNotificacion() {
        // Given
        Long id = 1L;
        Notificacion notificacion = crearNotificacion(id);
        NotificacionResponse response = crearResponse(id);

        when(notificacionRepository.findById(id))
                .thenReturn(Optional.of(notificacion));

        when(notificacionMapper.toResponse(notificacion))
                .thenReturn(response);

        // When
        NotificacionResponse resultado = notificacionService.obtenerPorId(id);

        // Then
        assertNotNull(resultado);
        assertEquals(id, resultado.getIdNotificacion());
        assertEquals(10L, resultado.getIdUsuario());
        assertEquals("Reserva creada correctamente", resultado.getMensaje());
        assertEquals("RESERVA", resultado.getTipo());
        assertFalse(resultado.getLeida());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaNotificacionNoEncontradaException() {
        // Given
        Long id = 50L;

        when(notificacionRepository.findById(id))
                .thenReturn(Optional.empty());

        // When / Then
        assertThrows(
                NotificacionNoEncontradaException.class,
                () -> notificacionService.obtenerPorId(id)
        );
    }

    @Test
    void eliminar_cuandoExiste_eliminaNotificacion() {
        // Given
        Long id = 2L;

        when(notificacionRepository.existsById(id))
                .thenReturn(true);

        // When
        notificacionService.eliminar(id);

        // Then
        verify(notificacionRepository).deleteById(id);
    }

    @Test
    void eliminar_cuandoNoExiste_lanzaNotificacionNoEncontradaException() {
        // Given
        Long id = 99L;

        when(notificacionRepository.existsById(id))
                .thenReturn(false);

        // When / Then
        assertThrows(
                NotificacionNoEncontradaException.class,
                () -> notificacionService.eliminar(id)
        );

        verify(notificacionRepository, never()).deleteById(id);
    }

    private Notificacion crearNotificacion(Long id) {
        Notificacion notificacion = new Notificacion();
        notificacion.setIdNotificacion(id);
        notificacion.setIdUsuario(10L);
        notificacion.setIdReserva(5L);
        notificacion.setIdCancelacion(null);
        notificacion.setMensaje("Reserva creada correctamente");
        notificacion.setTipo("RESERVA");
        notificacion.setLeida(false);
        return notificacion;
    }

    private NotificacionResponse crearResponse(Long id) {
        NotificacionResponse response = new NotificacionResponse();
        response.setIdNotificacion(id);
        response.setIdUsuario(10L);
        response.setIdReserva(5L);
        response.setIdCancelacion(null);
        response.setMensaje("Reserva creada correctamente");
        response.setTipo("RESERVA");
        response.setLeida(false);
        return response;
    }
}
