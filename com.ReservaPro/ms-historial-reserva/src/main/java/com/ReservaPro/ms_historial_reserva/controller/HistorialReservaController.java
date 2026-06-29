package com.ReservaPro.ms_historial_reserva.controller; // Paquete del controller

import com.ReservaPro.ms_historial_reserva.dto.request.HistorialReservaRequest; // DTO que recibe datos
import com.ReservaPro.ms_historial_reserva.dto.response.HistorialReservaResponse; // DTO que devuelve datos
import com.ReservaPro.ms_historial_reserva.model.EstadoReserva; // Enum para filtrar por estados
import com.ReservaPro.ms_historial_reserva.service.HistorialReservaService; // Service con lógica de negocio

import io.swagger.v3.oas.annotations.Operation; // Documenta cada endpoint
import io.swagger.v3.oas.annotations.Parameter; // Documenta parámetros
import io.swagger.v3.oas.annotations.parameters.RequestBody; // Documenta el body en Swagger
import io.swagger.v3.oas.annotations.responses.ApiResponse; // Documenta una respuesta HTTP
import io.swagger.v3.oas.annotations.responses.ApiResponses; // Agrupa respuestas HTTP
import io.swagger.v3.oas.annotations.tags.Tag; // Agrupa endpoints en Swagger

import jakarta.validation.Valid; // Activa validaciones del Request
import lombok.RequiredArgsConstructor; // Crea constructor automático

import org.springframework.format.annotation.DateTimeFormat; // Convierte fecha/hora desde URL
import org.springframework.http.HttpStatus; // Permite usar códigos HTTP
import org.springframework.http.ResponseEntity; // Permite devolver respuesta HTTP
import org.springframework.web.bind.annotation.*; // Importa anotaciones REST

import java.time.LocalDateTime; // Permite usar fecha y hora
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
            summary = "Obtener todos los historiales",
            description = "Retorna una lista de todos los historiales de reservas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista obtenida correctamente"
    )
    public ResponseEntity<List<HistorialReservaResponse>> obtenerHistoriales() {

        return ResponseEntity.ok(
                historialReservaService.obtener()
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
            @PathVariable Long id) {

        return ResponseEntity.ok(
                historialReservaService.obtenerPorId(id)
        );
    }

    @GetMapping("/reserva/{idReserva}") // GET /api/v1/historial-reservas/reserva/{idReserva}
    @Operation(
            summary = "Obtener historial por reserva",
            description = "Obtiene historiales asociados a una reserva"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Historiales encontrados"
    )
    public ResponseEntity<List<HistorialReservaResponse>> obtenerPorReserva(

            @Parameter(
                    description = "ID de la reserva",
                    example = "10",
                    required = true
            )
            @PathVariable Long idReserva) {

        return ResponseEntity.ok(
                historialReservaService.obtenerPorReserva(idReserva)
        );
    }

    @GetMapping("/estado-anterior/{estadoAnterior}") // GET /api/v1/historial-reservas/estado-anterior/PENDIENTE
    @Operation(
            summary = "Obtener historiales por estado anterior",
            description = "Obtiene historiales según el estado anterior de la reserva"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Historiales encontrados"
    )
    public ResponseEntity<List<HistorialReservaResponse>> obtenerPorEstadoAnterior(

            @Parameter(
                    description = "Estado anterior de la reserva",
                    example = "PENDIENTE",
                    required = true
            )
            @PathVariable EstadoReserva estadoAnterior) {

        return ResponseEntity.ok(
                historialReservaService.obtenerPorEstadoAnterior(estadoAnterior)
        );
    }

    @GetMapping("/estado-nuevo/{estadoNuevo}") // GET /api/v1/historial-reservas/estado-nuevo/CONFIRMADA
    @Operation(
            summary = "Obtener historiales por estado nuevo",
            description = "Obtiene historiales según el nuevo estado de la reserva"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Historiales encontrados"
    )
    public ResponseEntity<List<HistorialReservaResponse>> obtenerPorEstadoNuevo(

            @Parameter(
                    description = "Nuevo estado de la reserva",
                    example = "CONFIRMADA",
                    required = true
            )
            @PathVariable EstadoReserva estadoNuevo) {

        return ResponseEntity.ok(
                historialReservaService.obtenerPorEstadoNuevo(estadoNuevo)
        );
    }

    @GetMapping("/fecha/{fechaCambio}") // GET /api/v1/historial-reservas/fecha/2026-06-28T15:30:00
    @Operation(
            summary = "Obtener historiales por fecha de cambio",
            description = "Obtiene historiales según la fecha y hora de cambio"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Historiales encontrados"
    )
    public ResponseEntity<List<HistorialReservaResponse>> obtenerPorFechaCambio(

            @Parameter(
                    description = "Fecha y hora del cambio",
                    example = "2026-06-28T15:30:00",
                    required = true
            )
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime fechaCambio) {

        return ResponseEntity.ok(
                historialReservaService.obtenerPorFechaCambio(fechaCambio)
        );
    }

    @PostMapping // POST /api/v1/historial-reservas
    @Operation(
            summary = "Crear historial",
            description = "Crea un nuevo historial de reserva"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Historial creado correctamente"
    )
    public ResponseEntity<HistorialReservaResponse> crearHistorial(

            @RequestBody(description = "Datos del historial a crear", required = true)
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            HistorialReservaRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(historialReservaService.crear(request));
    }

    @PutMapping("/{id}") // PUT /api/v1/historial-reservas/{id}
    @Operation(
            summary = "Actualizar historial",
            description = "Actualiza un historial por ID"
    )
    public ResponseEntity<HistorialReservaResponse> actualizarHistorial(

            @Parameter(description = "ID del historial", required = true)
            @PathVariable Long id,

            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            HistorialReservaRequest request) {

        return ResponseEntity.ok(
                historialReservaService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}") // DELETE /api/v1/historial-reservas/{id}
    @Operation(
            summary = "Eliminar historial",
            description = "Elimina un historial por ID"
    )
    public ResponseEntity<Void> eliminarHistorial(

            @Parameter(description = "ID del historial", required = true)
            @PathVariable Long id) {

        historialReservaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}