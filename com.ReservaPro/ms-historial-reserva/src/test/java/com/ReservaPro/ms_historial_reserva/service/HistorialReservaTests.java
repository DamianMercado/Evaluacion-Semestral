package com.ReservaPro.ms_historial_reserva.service; // Paquete donde se encuentra la clase de prueba

import com.ReservaPro.ms_historial_reserva.dto.response.HistorialReservaResponse; // Importa el DTO de respuesta
import com.ReservaPro.ms_historial_reserva.exception.HistorialReservaNoEncontradaException; // Importa la excepción personalizada
import com.ReservaPro.ms_historial_reserva.mapper.HistorialReservaMapper; // Importa el Mapper
import com.ReservaPro.ms_historial_reserva.model.EstadoReserva; // Importa el Enum EstadoReserva
import com.ReservaPro.ms_historial_reserva.model.HistorialReserva; // Importa la entidad HistorialReserva
import com.ReservaPro.ms_historial_reserva.repository.HistorialReservaRepository; // Importa el repositorio

import org.jspecify.annotations.NonNull; // Indica que un objeto no puede ser nulo
import org.junit.jupiter.api.Test; // Permite crear métodos de prueba
import org.junit.jupiter.api.extension.ExtendWith; // Permite agregar extensiones a JUnit

import org.mockito.InjectMocks; // Crea el Service real e inyecta los mocks
import org.mockito.Mock; // Crea objetos simulados
import org.mockito.junit.jupiter.MockitoExtension; // Activa Mockito

import java.time.LocalDateTime; // Maneja fechas y horas
import java.util.Optional; // Permite manejar valores opcionales

import static org.junit.jupiter.api.Assertions.*; // Importa las validaciones de JUnit
import static org.mockito.Mockito.*; // Importa funciones de Mockito

@ExtendWith(MockitoExtension.class) // Activa Mockito para toda la clase
class HistorialReservaTests { // Clase de pruebas

    @Mock // Crea un repositorio falso
    private HistorialReservaRepository historialReservaRepository;

    @Mock // Crea un mapper falso
    private HistorialReservaMapper historialReservaMapper;

    @InjectMocks // Crea el Service real e inyecta los mocks
    private HistorialReservaService historialReservaService;

    @Test // Indica que este método es una prueba
    void obtenerPorId_cuandoExiste_retornaHistorial() {

        // Given = Preparación de datos

        Long id = 1L; // ID de prueba

        HistorialReserva historial = crearHistorial(id); // Crea una entidad falsa

        HistorialReservaResponse response = crearResponse(id); // Crea un DTO falso

        when(historialReservaRepository.findById(id)) // Simula la búsqueda en BD
                .thenReturn(Optional.of(historial)); // Devuelve el historial simulado

        when(historialReservaMapper.toResponse(historial)) // Simula el mapper
                .thenReturn(response); // Devuelve el DTO simulado

        // When = Ejecuta el método real

        HistorialReservaResponse resultado =
                historialReservaService.obtenerPorId(id);

        // Then = Validaciones

        assertNotNull(resultado); // Verifica que el resultado no sea nulo

        assertEquals(id, resultado.getIdHistorial()); // Verifica el ID

        assertEquals(10L, resultado.getIdReserva()); // Verifica el ID de reserva
    }

    @Test // Nueva prueba
    void obtenerPorId_cuandoNoExiste_lanzaException() {

        // Given

        Long id = 99L; // ID inexistente

        when(historialReservaRepository.findById(id)) // Simula búsqueda
                .thenReturn(Optional.empty()); // No encuentra nada

        // When / Then

        assertThrows( // Verifica que lance la excepción
                HistorialReservaNoEncontradaException.class,
                () -> historialReservaService.obtenerPorId(id)
        );
    }

    @Test // Nueva prueba
    void eliminar_cuandoExiste_eliminaHistorial() {

        // Given

        Long id = 1L; // ID de prueba

        when(historialReservaRepository.existsById(id)) // Simula existencia
                .thenReturn(true);

        // When

        historialReservaService.eliminar(id); // Ejecuta eliminar

        // Then

        verify(historialReservaRepository) // Verifica la eliminación
                .deleteById(id);
    }

    @Test // Nueva prueba
    void eliminar_cuandoNoExiste_lanzaException() {

        // Given

        Long id = 50L; // ID inexistente

        when(historialReservaRepository.existsById(id))
                .thenReturn(false); // Simula que no existe

        // When / Then

        assertThrows( // Verifica que lance excepción
                HistorialReservaNoEncontradaException.class,
                () -> historialReservaService.eliminar(id)
        );

        verify(historialReservaRepository, never()) // Verifica que nunca eliminó
                .deleteById(id);
    }

    private HistorialReserva crearHistorial(Long id) { // Método auxiliar

        HistorialReserva historial = new HistorialReserva(); // Crea entidad falsa

        historial.setIdHistorial(id); // Asigna ID historial
        historial.setIdReserva(10L); // Asigna ID reserva
        historial.setEstadoAnterior(EstadoReserva.PENDIENTE); // Estado anterior
        historial.setEstadoNuevo(EstadoReserva.CONFIRMADA); // Estado nuevo
        historial.setObservacion("Reserva confirmada"); // Observación
        historial.setFechaCambio(LocalDateTime.now()); // Fecha actual

        return historial; // Retorna la entidad
    }

    private @NonNull HistorialReservaResponse crearResponse(Long id) { // Método auxiliar

        HistorialReservaResponse response =
                new HistorialReservaResponse(); // Crea DTO falso

        response.setIdHistorial(id); // Asigna ID historial
        response.setIdReserva(10L); // Asigna ID reserva
        response.setEstadoAnterior(EstadoReserva.PENDIENTE); // Estado anterior
        response.setEstadoNuevo(EstadoReserva.CONFIRMADA); // Estado nuevo
        response.setObservacion("Reserva confirmada"); // Observación
        response.setFechaCambio(LocalDateTime.now()); // Fecha actual

        return response; // Retorna el DTO
    }
}