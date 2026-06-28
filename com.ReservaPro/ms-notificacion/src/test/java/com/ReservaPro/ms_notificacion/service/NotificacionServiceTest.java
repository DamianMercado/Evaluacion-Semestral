package com.ReservaPro.ms_notificacion.service; // Indica el paquete donde está la clase

import com.ReservaPro.ms_notificacion.dto.response.NotificacionResponse; // Importa el DTO de respuesta
import com.ReservaPro.ms_notificacion.exception.NotificacionNoEncontradaException; // Importa la excepción personalizada
import com.ReservaPro.ms_notificacion.mapper.NotificacionMapper; // Importa el mapper
import com.ReservaPro.ms_notificacion.model.Notificacion; // Importa la entidad Notificacion
import com.ReservaPro.ms_notificacion.repository.NotificacionRepository; // Importa el repositorio

import org.junit.jupiter.api.Test; // Permite crear métodos de prueba
import org.junit.jupiter.api.extension.ExtendWith; // Permite usar extensiones en JUnit

import org.mockito.InjectMocks; // Crea el Service real e inyecta los mocks
import org.mockito.Mock; // Crea objetos simulados (mocks)
import org.mockito.junit.jupiter.MockitoExtension; // Activa Mockito

import java.util.Optional; // Permite usar Optional

import static org.junit.jupiter.api.Assertions.*; // Importa las validaciones de JUnit
import static org.mockito.Mockito.*; // Importa las funciones de Mockito

@ExtendWith(MockitoExtension.class) // Activa Mockito para toda la clase
class NotificacionServiceTest { // Clase de pruebas

    @Mock // Crea un repositorio falso
    private NotificacionRepository notificacionRepository;

    @Mock // Crea un mapper falso
    private NotificacionMapper notificacionMapper;

    @InjectMocks // Crea el Service real e inyecta los mocks
    private NotificacionService notificacionService;

    @Test // Indica que este método es una prueba
    void obtenerPorId_cuandoExiste_retornaNotificacion() {

        // Given = Preparación de datos

        Long id = 1L; // ID de prueba

        Notificacion notificacion = crearNotificacion(id); // Crea una entidad falsa

        NotificacionResponse response = crearResponse(id); // Crea un DTO falso

        when(notificacionRepository.findById(id)) // Simula búsqueda en BD
                .thenReturn(Optional.of(notificacion)); // Devuelve la entidad simulada

        when(notificacionMapper.toResponse(notificacion)) // Simula el mapper
                .thenReturn(response); // Devuelve el DTO simulado

        // When = Ejecuta el método real

        NotificacionResponse resultado =
                notificacionService.obtenerPorId(id);

        // Then = Verificaciones

        assertNotNull(resultado); // Verifica que no sea nulo

        assertEquals(id, resultado.getIdNotificacion()); // Verifica el ID

        assertEquals(10L, resultado.getIdUsuario()); // Verifica el usuario

        assertEquals("Reserva creada correctamente",
                resultado.getMensaje()); // Verifica el mensaje

        assertEquals("RESERVA",
                resultado.getTipo()); // Verifica el tipo

        assertFalse(resultado.getLeida()); // Verifica que no esté leída
    }

    @Test // Nueva prueba
    void obtenerPorId_cuandoNoExiste_lanzaNotificacionNoEncontradaException() {

        // Given

        Long id = 50L; // ID inexistente

        when(notificacionRepository.findById(id)) // Simula búsqueda
                .thenReturn(Optional.empty()); // No encuentra nada

        // When / Then

        assertThrows( // Verifica que lance la excepción
                NotificacionNoEncontradaException.class,
                () -> notificacionService.obtenerPorId(id)
        );
    }

    @Test // Nueva prueba
    void eliminar_cuandoExiste_eliminaNotificacion() {

        // Given

        Long id = 2L; // ID de prueba

        when(notificacionRepository.existsById(id)) // Simula existencia
                .thenReturn(true);

        // When

        notificacionService.eliminar(id); // Ejecuta eliminar

        // Then

        verify(notificacionRepository).deleteById(id); // Verifica eliminación
    }

    @Test // Nueva prueba
    void eliminar_cuandoNoExiste_lanzaNotificacionNoEncontradaException() {

        // Given

        Long id = 99L; // ID inexistente

        when(notificacionRepository.existsById(id))
                .thenReturn(false); // Simula que no existe

        // When / Then

        assertThrows(
                NotificacionNoEncontradaException.class, // Espera excepción
                () -> notificacionService.eliminar(id)
        );

        verify(notificacionRepository, never())
                .deleteById(id); // Verifica que nunca eliminó
    }

    private Notificacion crearNotificacion(Long id) {

        Notificacion notificacion = new Notificacion(); // Crea entidad falsa

        notificacion.setIdNotificacion(id); // Asigna ID
        notificacion.setIdUsuario(10L); // Asigna usuario
        notificacion.setIdReserva(5L); // Asigna reserva
        notificacion.setIdCancelacion(null); // Sin cancelación
        notificacion.setMensaje("Reserva creada correctamente"); // Mensaje
        notificacion.setTipo("RESERVA"); // Tipo
        notificacion.setLeida(false); // Estado

        return notificacion; // Retorna la entidad
    }

    private NotificacionResponse crearResponse(Long id) {

        NotificacionResponse response =
                new NotificacionResponse(); // Crea DTO falso

        response.setIdNotificacion(id); // Asigna ID
        response.setIdUsuario(10L); // Asigna usuario
        response.setIdReserva(5L); // Asigna reserva
        response.setIdCancelacion(null); // Sin cancelación
        response.setMensaje("Reserva creada correctamente"); // Mensaje
        response.setTipo("RESERVA"); // Tipo
        response.setLeida(false); // Estado

        return response; // Retorna el DTO
    }
}