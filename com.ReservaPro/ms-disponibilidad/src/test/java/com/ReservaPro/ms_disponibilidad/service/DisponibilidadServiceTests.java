package com.ReservaPro.ms_disponibilidad.service; // Define el paquete donde se encuentra la clase de prueba

import com.ReservaPro.ms_disponibilidad.dto.response.DisponibilidadResponse; // Importa el DTO Response
import com.ReservaPro.ms_disponibilidad.exception.DisponibilidadNotFoundException; // Importa la excepción personalizada
import com.ReservaPro.ms_disponibilidad.mapper.DisponibilidadMapper; // Importa el Mapper
import com.ReservaPro.ms_disponibilidad.model.Disponibilidad; // Importa la entidad Disponibilidad
import com.ReservaPro.ms_disponibilidad.model.EstadoDisponibilidad; // Importa el Enum EstadoDisponibilidad
import com.ReservaPro.ms_disponibilidad.repository.DisponibilidadRepository; // Importa el Repository

import org.junit.jupiter.api.Test; // Permite crear métodos de prueba
import org.junit.jupiter.api.extension.ExtendWith; // Permite agregar extensiones a JUnit

import org.mockito.InjectMocks; // Crea el Service real e inyecta los mocks
import org.mockito.Mock; // Crea objetos simulados (Mocks)
import org.mockito.junit.jupiter.MockitoExtension; // Activa Mockito

import java.time.LocalDate; // Permite manejar fechas
import java.time.LocalTime; // Permite manejar horas
import java.util.Optional; // Permite manejar valores opcionales

import static org.junit.jupiter.api.Assertions.*; // Importa las validaciones de JUnit
import static org.mockito.Mockito.*; // Importa funciones de Mockito

@ExtendWith(MockitoExtension.class) // Activa Mockito para toda la clase
class DisponibilidadServiceTests { // Clase de pruebas unitarias

    @Mock // Crea un repositorio falso
    private DisponibilidadRepository disponibilidadRepository;

    @Mock // Crea un mapper falso
    private DisponibilidadMapper disponibilidadMapper;

    @InjectMocks // Crea el Service real e inyecta los mocks
    private DisponibilidadService disponibilidadService;

    @Test // Indica que este método es una prueba
    void obtenerPorId_cuandoExiste_retornaDisponibilidad() {

        Long id = 1L; // ID de prueba

        Disponibilidad disponibilidad =
                crearDisponibilidad(id); // Crea entidad simulada

        DisponibilidadResponse response =
                crearResponse(id); // Crea DTO Response simulado

        when(disponibilidadRepository.findById(id)) // Simula búsqueda en BD
                .thenReturn(Optional.of(disponibilidad)); // Retorna la entidad simulada

        when(disponibilidadMapper.toResponse(disponibilidad)) // Simula el Mapper
                .thenReturn(response); // Retorna el DTO simulado

        DisponibilidadResponse resultado =
                disponibilidadService.obtenerPorId(id); // Ejecuta el método real

        assertNotNull(resultado); // Verifica que el resultado no sea nulo

        assertEquals(id,
                resultado.getIdDisponibilidad()); // Verifica el ID

        assertEquals(
                EstadoDisponibilidad.DISPONIBLE,
                resultado.getEstado()
        ); // Verifica el estado
    }

    @Test // Nueva prueba
    void obtenerPorId_cuandoNoExiste_lanzaException() {

        Long id = 99L; // ID inexistente

        when(disponibilidadRepository.findById(id)) // Simula búsqueda
                .thenReturn(Optional.empty()); // No encuentra datos

        assertThrows( // Verifica que lance la excepción
                DisponibilidadNotFoundException.class,
                () -> disponibilidadService.obtenerPorId(id)
        );
    }

    @Test // Nueva prueba
    void eliminar_cuandoExiste_eliminaDisponibilidad() {

        Long id = 1L; // ID de prueba

        when(disponibilidadRepository.existsById(id)) // Simula que existe
                .thenReturn(true);

        disponibilidadService.eliminar(id); // Ejecuta eliminar

        verify(disponibilidadRepository) // Verifica que se eliminó
                .deleteById(id);
    }

    @Test // Nueva prueba
    void eliminar_cuandoNoExiste_lanzaException() {

        Long id = 50L; // ID inexistente

        when(disponibilidadRepository.existsById(id))
                .thenReturn(false); // Simula que no existe

        assertThrows( // Verifica que lance excepción
                DisponibilidadNotFoundException.class,
                () -> disponibilidadService.eliminar(id)
        );

        verify(disponibilidadRepository, never()) // Verifica que nunca eliminó
                .deleteById(id);
    }

    private Disponibilidad crearDisponibilidad(Long id) { // Método auxiliar

        Disponibilidad disponibilidad =
                new Disponibilidad(); // Crea entidad simulada

        disponibilidad.setIdDisponibilidad(id); // Asigna ID
        disponibilidad.setFecha(LocalDate.now()); // Asigna fecha actual
        disponibilidad.setHoraInicio(LocalTime.of(9, 0)); // Hora inicio
        disponibilidad.setHoraFin(LocalTime.of(18, 0)); // Hora término
        disponibilidad.setCuposDisponibles(5); // Cupos disponibles
        disponibilidad.setCuposTotales(10); // Cupos totales

        disponibilidad.setEstado(
                EstadoDisponibilidad.DISPONIBLE
        ); // Asigna estado

        return disponibilidad; // Retorna la entidad
    }

    private DisponibilidadResponse crearResponse(Long id) { // Método auxiliar

        DisponibilidadResponse response =
                new DisponibilidadResponse(); // Crea DTO Response

        response.setIdDisponibilidad(id); // Asigna ID
        response.setFecha(LocalDate.now()); // Asigna fecha actual
        response.setHoraInicio(LocalTime.of(9, 0)); // Hora inicio
        response.setHoraFin(LocalTime.of(18, 0)); // Hora término
        response.setCuposDisponibles(5); // Cupos disponibles
        response.setCuposTotales(10); // Cupos totales

        response.setEstado(
                EstadoDisponibilidad.DISPONIBLE
        ); // Asigna estado

        return response; // Retorna el DTO
    }
}