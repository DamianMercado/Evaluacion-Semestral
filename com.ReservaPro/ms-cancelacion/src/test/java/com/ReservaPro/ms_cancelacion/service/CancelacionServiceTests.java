package com.ReservaPro.ms_cancelacion.service; // Paquete donde está la clase de test

import com.ReservaPro.ms_cancelacion.dto.response.CancelacionResponse; // DTO que devuelve el Service
import com.ReservaPro.ms_cancelacion.exception.CancelacionNotFoundException; // Excepción cuando no existe cancelación
import com.ReservaPro.ms_cancelacion.mapper.CancelacionMapper; // Mapper para convertir entidad a Response
import com.ReservaPro.ms_cancelacion.model.Cancelacion; // Entidad Cancelacion
import com.ReservaPro.ms_cancelacion.model.EstadoReembolso; // Enum del estado del reembolso
import com.ReservaPro.ms_cancelacion.repository.CancelacionRepository; // Repositorio de cancelación

import org.junit.jupiter.api.Test; // Permite crear pruebas unitarias
import org.junit.jupiter.api.extension.ExtendWith; // Permite usar extensiones de JUnit

import org.mockito.InjectMocks; // Crea el Service real e inyecta los mocks
import org.mockito.Mock; // Crea objetos falsos simulados
import org.mockito.junit.jupiter.MockitoExtension; // Activa Mockito

import java.time.LocalDate; // Permite usar fechas
import java.util.Optional; // Permite simular findById con dato o vacío

import static org.junit.jupiter.api.Assertions.*; // Importa assertNotNull, assertEquals, assertThrows
import static org.mockito.Mockito.*; // Importa when, verify, never

@ExtendWith(MockitoExtension.class) // Activa Mockito para esta clase
class CancelacionServiceTests { // Clase de pruebas unitarias

    @Mock // Crea un repositorio falso, no usa MySQL real
    private CancelacionRepository cancelacionRepository;

    @Mock // Crea un mapper falso
    private CancelacionMapper cancelacionMapper;

    @InjectMocks // Crea el CancelacionService real usando los mocks
    private CancelacionService cancelacionService;

    @Test // Indica que este método es una prueba
    void obtenerPorId_cuandoExiste_retornaCancelacion() {

        Long id = 1L; // ID de prueba

        Cancelacion cancelacion = crearCancelacion(id); // Crea una cancelación falsa

        CancelacionResponse response = crearResponse(id); // Crea una respuesta falsa

        when(cancelacionRepository.findById(id)) // Cuando el service busque por ID
                .thenReturn(Optional.of(cancelacion)); // Devuelve la cancelación falsa

        when(cancelacionMapper.toResponse(cancelacion)) // Cuando el mapper convierta la entidad
                .thenReturn(response); // Devuelve el response falso

        CancelacionResponse resultado =
                cancelacionService.obtenerPorId(id); // Ejecuta el método real del service

        assertNotNull(resultado); // Verifica que el resultado no sea nulo

        assertEquals(id, resultado.getIdCancelacion()); // Verifica que el ID sea correcto

        assertEquals(
                "Cambio de fecha",
                resultado.getMotivo()
        ); // Verifica el motivo

        assertEquals(
                EstadoReembolso.PENDIENTE,
                resultado.getEstadoReembolso()
        ); // Verifica el estado del reembolso
    }

    @Test // Segunda prueba
    void obtenerPorId_cuandoNoExiste_lanzaException() {

        Long id = 99L; // ID que no existe

        when(cancelacionRepository.findById(id)) // Simula búsqueda por ID
                .thenReturn(Optional.empty()); // Devuelve vacío, como si no existiera

        assertThrows( // Verifica que lance una excepción
                CancelacionNotFoundException.class,
                () -> cancelacionService.obtenerPorId(id)
        );
    }

    @Test // Tercera prueba
    void eliminar_cuandoExiste_eliminaCancelacion() {

        Long id = 1L; // ID de prueba

        when(cancelacionRepository.existsById(id)) // Simula que el ID existe
                .thenReturn(true);

        cancelacionService.eliminar(id); // Ejecuta el método eliminar del service

        verify(cancelacionRepository) // Verifica que el repositorio haya eliminado
                .deleteById(id);
    }

    @Test // Cuarta prueba
    void eliminar_cuandoNoExiste_lanzaException() {

        Long id = 50L; // ID inexistente

        when(cancelacionRepository.existsById(id)) // Simula que no existe
                .thenReturn(false);

        assertThrows( // Verifica que lance excepción
                CancelacionNotFoundException.class,
                () -> cancelacionService.eliminar(id)
        );

        verify(cancelacionRepository, never()) // Verifica que nunca se llamó deleteById
                .deleteById(id);
    }

    private Cancelacion crearCancelacion(Long id) { // Método auxiliar para crear entidad falsa

        Cancelacion cancelacion = new Cancelacion(); // Crea objeto Cancelacion

        cancelacion.setIdCancelacion(id); // Asigna ID
        cancelacion.setMotivo("Cambio de fecha"); // Asigna motivo
        cancelacion.setFechaCancelacion(LocalDate.now()); // Asigna fecha actual
        cancelacion.setEstadoReembolso(
                EstadoReembolso.PENDIENTE
        ); // Asigna estado pendiente

        return cancelacion; // Devuelve la entidad falsa
    }

    private CancelacionResponse crearResponse(Long id) { // Método auxiliar para crear Response falso

        CancelacionResponse response =
                new CancelacionResponse(); // Crea objeto Response

        response.setIdCancelacion(id); // Asigna ID
        response.setMotivo("Cambio de fecha"); // Asigna motivo
        response.setFechaCancelacion(LocalDate.now()); // Asigna fecha actual
        response.setEstadoReembolso(
                EstadoReembolso.PENDIENTE
        ); // Asigna estado pendiente

        return response; // Devuelve el response falso
    }
}