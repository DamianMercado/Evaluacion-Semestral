package com.ReservaPro.ms_disponibilidad.controller;

import com.ReservaPro.ms_disponibilidad.dto.request.DisponibilidadRequest;
import com.ReservaPro.ms_disponibilidad.dto.response.DisponibilidadResponse;
import com.ReservaPro.ms_disponibilidad.service.DisponibilidadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/disponibilidades")
@RequiredArgsConstructor
@Tag(
        name = "Disponibilidades",
        description = "Operaciones relacionadas con las disponibilidades"
)
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    @GetMapping
    @Operation(
            summary = "Obtener todas las disponibilidades",
            description = "Retorna una lista de todas las disponibilidades"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista obtenida correctamente")
    })
    public ResponseEntity<List<DisponibilidadResponse>> obtenerDisponibilidades() {

        return ResponseEntity.ok(
                disponibilidadService.obtener()
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener disponibilidad por ID",
            description = "Obtiene una disponibilidad según su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Disponibilidad encontrada"),
            @ApiResponse(responseCode = "404",
                    description = "Disponibilidad no encontrada")
    })
    public ResponseEntity<DisponibilidadResponse> obtenerDisponibilidad(

            @Parameter(
                    description = "ID de la disponibilidad",
                    required = true
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                disponibilidadService.obtenerPorId(id)
        );
    }

    @GetMapping("/fecha/{fecha}")
    @Operation(
            summary = "Obtener disponibilidades por fecha",
            description = "Obtiene disponibilidades según una fecha"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Disponibilidades encontradas")
    })
    public ResponseEntity<List<DisponibilidadResponse>> obtenerPorFecha(

            @Parameter(
                    description = "Fecha de disponibilidad",
                    example = "2026-06-10",
                    required = true
            )
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha) {

        return ResponseEntity.ok(
                disponibilidadService.obtenerPorFecha(fecha)
        );
    }

    @GetMapping("/activas")
    @Operation(
            summary = "Obtener disponibilidades activas",
            description = "Obtiene todas las disponibilidades activas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Disponibilidades activas encontradas")
    })
    public ResponseEntity<List<DisponibilidadResponse>> obtenerActivas() {

        return ResponseEntity.ok(
                disponibilidadService.obtenerActivas()
        );
    }

    @PostMapping
    @Operation(
            summary = "Crear una disponibilidad",
            description = "Crea una nueva disponibilidad en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Disponibilidad creada correctamente"),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos")
    })
    public ResponseEntity<DisponibilidadResponse> crearDisponibilidad(

            @RequestBody(
                    description = "Datos de la disponibilidad a crear",
                    required = true
            )
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            DisponibilidadRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(disponibilidadService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una disponibilidad",
            description = "Actualiza una disponibilidad existente por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Disponibilidad actualizada"),
            @ApiResponse(responseCode = "404",
                    description = "Disponibilidad no encontrada")
    })
    public ResponseEntity<DisponibilidadResponse> actualizarDisponibilidad(

            @Parameter(
                    description = "ID de la disponibilidad",
                    required = true
            )
            @PathVariable Long id,

            @RequestBody(
                    description = "Datos actualizados de la disponibilidad",
                    required = true
            )
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            DisponibilidadRequest request) {

        return ResponseEntity.ok(
                disponibilidadService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una disponibilidad",
            description = "Elimina una disponibilidad por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Disponibilidad eliminada"),
            @ApiResponse(responseCode = "404",
                    description = "Disponibilidad no encontrada")
    })
    public ResponseEntity<Void> eliminarDisponibilidad(

            @Parameter(
                    description = "ID de la disponibilidad",
                    required = true
            )
            @PathVariable Long id) {

        disponibilidadService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}