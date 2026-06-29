package com.ReservaPro.ms_cancelacion.controller; // Paquete donde está el Controller

import com.ReservaPro.ms_cancelacion.dto.request.CancelacionRequest; // DTO que recibe datos del cliente
import com.ReservaPro.ms_cancelacion.dto.response.CancelacionResponse; // DTO que devuelve datos al cliente
import com.ReservaPro.ms_cancelacion.model.EstadoReembolso; // Enum para filtrar por estado
import com.ReservaPro.ms_cancelacion.service.CancelacionService; // Service con la lógica de negocio

import io.swagger.v3.oas.annotations.Operation; // Documenta operaciones en Swagger
import io.swagger.v3.oas.annotations.Parameter; // Documenta parámetros en Swagger
import io.swagger.v3.oas.annotations.parameters.RequestBody; // Documenta el body en Swagger
import io.swagger.v3.oas.annotations.responses.ApiResponse; // Documenta respuestas HTTP
import io.swagger.v3.oas.annotations.responses.ApiResponses; // Agrupa varias respuestas HTTP
import io.swagger.v3.oas.annotations.tags.Tag; // Agrupa endpoints en Swagger

import jakarta.validation.Valid; // Activa validaciones del Request
import lombok.RequiredArgsConstructor; // Crea constructor automático

import org.springframework.format.annotation.DateTimeFormat; // Convierte texto de fecha a LocalDate
import org.springframework.http.HttpStatus; // Permite usar estados HTTP
import org.springframework.http.ResponseEntity; // Permite devolver respuestas HTTP
import org.springframework.web.bind.annotation.*; // Importa anotaciones REST

import java.time.LocalDate; // Permite trabajar con fechas
import java.util.List; // Permite devolver listas

@RestController // Indica que esta clase es un controller REST
@RequestMapping("/api/v1/cancelaciones") // Ruta base del microservicio
@RequiredArgsConstructor // Inyecta el service por constructor
@Tag(
        name = "Cancelaciones", // Nombre que aparece en Swagger
        description = "Operaciones relacionadas con las cancelaciones" // Descripción en Swagger
)
public class CancelacionController {

    private final CancelacionService cancelacionService; // Service que procesa la lógica

    @GetMapping // GET /api/v1/cancelaciones
    @Operation(
            summary = "Obtener todas las cancelaciones", // Resumen en Swagger
            description = "Retorna una lista de todas las cancelaciones" // Descripción en Swagger
    )
    @ApiResponse(
            responseCode = "200", // Código HTTP correcto
            description = "Lista obtenida correctamente" // Mensaje Swagger
    )
    public ResponseEntity<List<CancelacionResponse>> obtenerCancelaciones() {

        return ResponseEntity.ok(
                cancelacionService.obtener()
        ); // Devuelve lista con HTTP 200
    }

    @GetMapping("/{id}") // GET /api/v1/cancelaciones/{id}
    @Operation(
            summary = "Obtener cancelación por ID",
            description = "Obtiene una cancelación según su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cancelación encontrada"),
            @ApiResponse(responseCode = "404", description = "Cancelación no encontrada")
    })
    public ResponseEntity<CancelacionResponse> obtenerCancelacion(

            @Parameter(description = "ID de la cancelación", required = true)
            @PathVariable Long id) { // Recibe el ID desde la URL

        return ResponseEntity.ok(
                cancelacionService.obtenerPorId(id)
        ); // Llama al service y devuelve la cancelación
    }

    @GetMapping("/reserva/{idReserva}") // GET /api/v1/cancelaciones/reserva/{idReserva}
    public ResponseEntity<List<CancelacionResponse>> obtenerPorReserva(
            @PathVariable Long idReserva) { // Recibe el ID de reserva

        return ResponseEntity.ok(
                cancelacionService.obtenerPorReserva(idReserva)
        ); // Devuelve cancelaciones de esa reserva
    }

    @GetMapping("/estado/{estadoReembolso}") // GET /api/v1/cancelaciones/estado/PENDIENTE
    public ResponseEntity<List<CancelacionResponse>> obtenerPorEstado(
            @PathVariable EstadoReembolso estadoReembolso) { // Recibe estado del enum

        return ResponseEntity.ok(
                cancelacionService.obtenerPorEstado(estadoReembolso)
        ); // Devuelve cancelaciones por estado
    }

    @GetMapping("/fecha/{fechaCancelacion}") // GET /api/v1/cancelaciones/fecha/2026-06-28
    public ResponseEntity<List<CancelacionResponse>> obtenerPorFecha(

            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaCancelacion) { // Convierte la fecha de texto a LocalDate

        return ResponseEntity.ok(
                cancelacionService.obtenerPorFecha(fechaCancelacion)
        ); // Devuelve cancelaciones por fecha
    }

    @PostMapping // POST /api/v1/cancelaciones
    @Operation(
            summary = "Crear una cancelación",
            description = "Crea una nueva cancelación en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cancelación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<CancelacionResponse> crearCancelacion(

            @RequestBody(description = "Datos de la cancelación a crear", required = true)
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            CancelacionRequest cancelacion) { // Recibe JSON del cliente

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cancelacionService.crear(cancelacion)); // Crea y devuelve HTTP 201
    }

    @PutMapping("/{id}") // PUT /api/v1/cancelaciones/{id}
    public ResponseEntity<CancelacionResponse> actualizarCancelacion(

            @PathVariable Long id, // ID que viene en la URL

            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            CancelacionRequest cancelacion) { // Datos nuevos desde JSON

        return ResponseEntity.ok(
                cancelacionService.actualizar(id, cancelacion)
        ); // Actualiza y devuelve HTTP 200
    }

    @DeleteMapping("/{id}") // DELETE /api/v1/cancelaciones/{id}
    public ResponseEntity<Void> eliminarCancelacion(
            @PathVariable Long id) { // Recibe ID desde URL

        cancelacionService.eliminar(id); // Llama al service para eliminar

        return ResponseEntity.noContent().build(); // Devuelve HTTP 204 sin contenido
    }
}