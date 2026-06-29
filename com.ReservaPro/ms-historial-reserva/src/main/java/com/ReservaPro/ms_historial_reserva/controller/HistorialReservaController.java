package com.ReservaPro.ms_historial_reserva.controller; // Paquete del controller

import com.ReservaPro.ms_historial_reserva.dto.request.HistorialReservaRequest; // DTO que recibe datos
import com.ReservaPro.ms_historial_reserva.dto.response.HistorialReservaResponse; // DTO que devuelve datos
import com.ReservaPro.ms_historial_reserva.service.HistorialReservaService; // Service con lógica de negocio

import io.swagger.v3.oas.annotations.Operation; // Documenta cada endpoint
import io.swagger.v3.oas.annotations.Parameter; // Documenta parámetros
import io.swagger.v3.oas.annotations.parameters.RequestBody; // Documenta el body en Swagger
import io.swagger.v3.oas.annotations.responses.ApiResponse; // Documenta una respuesta HTTP
import io.swagger.v3.oas.annotations.responses.ApiResponses; // Agrupa respuestas HTTP
import io.swagger.v3.oas.annotations.tags.Tag; // Agrupa endpoints en Swagger

import jakarta.validation.Valid; // Activa validaciones del Request
import lombok.RequiredArgsConstructor; // Crea constructor automático

import org.springframework.http.HttpStatus; // Permite usar códigos HTTP
import org.springframework.http.ResponseEntity; // Permite devolver respuesta HTTP
import org.springframework.web.bind.annotation.*; // Importa anotaciones REST

import java.util.List; // Permite usar listas

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/v1/historial-reservas") // Ruta base del microservicio
@RequiredArgsConstructor // Inyecta el Service por constructor
@Tag(
        name = "Historial de Reservas", // Nombre del grupo en Swagger
        description = "Operaciones relacionadas con el historial de reservas" // Descripción Swagger
)
public class HistorialReservaController {

    private final HistorialReservaService historialReservaService; // Service usado por el controller

    @GetMapping // GET /api/v1/historial-reservas
    @Operation(
            summary = "Obtener todos los historiales", // Título Swagger
            description = "Retorna una lista de todos los historiales de reservas" // Descripción
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", // Código HTTP exitoso
                    description = "Lista obtenida correctamente")
    })
    public ResponseEntity<List<HistorialReservaResponse>> obtenerHistoriales() {

        return ResponseEntity.ok( // Devuelve HTTP 200
                historialReservaService.obtener() // Llama al service
        );
    }

    @GetMapping("/{id}") // GET /api/v1/historial-reservas/{id}
    @Operation(
            summary = "Obtener historial por ID",
            description = "Obtiene un historial según su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial encontrado"),
            @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    public ResponseEntity<HistorialReservaResponse> obtenerHistorial(

            @Parameter(description = "ID del historial", required = true)
            @PathVariable Long id) { // Recibe el ID desde la URL

        return ResponseEntity.ok(
                historialReservaService.obtenerPorId(id)
        );
    }

    @GetMapping("/reserva/{idReserva}") // GET /api/v1/historial-reservas/reserva/{idReserva}
    public ResponseEntity<List<HistorialReservaResponse>> obtenerPorReserva(
            @PathVariable Long idReserva) {

        return ResponseEntity.ok(
                historialReservaService.obtenerPorReserva(idReserva)
        );
    }

    @PostMapping // POST /api/v1/historial-reservas
    public ResponseEntity<HistorialReservaResponse> crearHistorial(
            @RequestBody(description = "Datos del historial a crear", required = true)
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            HistorialReservaRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED) // Devuelve 201
                .body(historialReservaService.crear(request));
    }

    @PutMapping("/{id}") // PUT /api/v1/historial-reservas/{id}
    public ResponseEntity<HistorialReservaResponse> actualizarHistorial(
            @PathVariable Long id,
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            HistorialReservaRequest request) {

        return ResponseEntity.ok(
                historialReservaService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}") // DELETE /api/v1/historial-reservas/{id}
    public ResponseEntity<Void> eliminarHistorial(@PathVariable Long id) {

        historialReservaService.eliminar(id); // Elimina desde el service

        return ResponseEntity.noContent().build(); // Devuelve 204 sin cuerpo
    }
}