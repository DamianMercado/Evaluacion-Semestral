package com.ReservaPro.ms_calificacion.controller;

import com.ReservaPro.ms_calificacion.dto.request.CalificacionRequest;
import com.ReservaPro.ms_calificacion.dto.response.CalificacionResponse;
import com.ReservaPro.ms_calificacion.enums.EstadoCalificacion;
import com.ReservaPro.ms_calificacion.service.CalificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/calificaciones")
@RequiredArgsConstructor
@Tag(name = "Calificaciones", description = "Operaciones relacionadas con las calificaciones")
public class CalificacionController {

    private final CalificacionService calificacionService;

    @GetMapping
    @Operation(summary = "Obtener todas las calificaciones")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<CalificacionResponse>> obtenerTodas() {
        return ResponseEntity.ok(calificacionService.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener calificación por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calificación encontrada"),
            @ApiResponse(responseCode = "404", description = "Calificación no encontrada")
    })
    public ResponseEntity<CalificacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(calificacionService.obtenerPorId(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Obtener calificaciones por usuario")
    public ResponseEntity<List<CalificacionResponse>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(calificacionService.obtenerPorUsuario(idUsuario));
    }

    @GetMapping("/reserva/{idReserva}")
    @Operation(summary = "Obtener calificaciones por reserva")
    public ResponseEntity<List<CalificacionResponse>> obtenerPorReserva(@PathVariable Long idReserva) {
        return ResponseEntity.ok(calificacionService.obtenerPorReserva(idReserva));
    }

    @GetMapping("/promedio")
    @Operation(summary = "Obtener promedio de calificaciones")
    public ResponseEntity<Double> obtenerPromedio() {
        return ResponseEntity.ok(calificacionService.obtenerPromedioCalificaciones());
    }

    @PostMapping
    @Operation(summary = "Crear calificación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Calificación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<CalificacionResponse> crear(@Valid @RequestBody CalificacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(calificacionService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar calificación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calificación actualizada"),
            @ApiResponse(responseCode = "404", description = "Calificación no encontrada")
    })
    public ResponseEntity<CalificacionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CalificacionRequest request) {
        return ResponseEntity.ok(calificacionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar calificación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Calificación eliminada"),
            @ApiResponse(responseCode = "404", description = "Calificación no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        calificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/publicar")
    @Operation(summary = "Publicar calificación")
    public ResponseEntity<CalificacionResponse> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(calificacionService.publicar(id));
    }

    @PatchMapping("/{id}/moderar")
    @Operation(summary = "Moderar calificación")
    public ResponseEntity<CalificacionResponse> moderar(
            @PathVariable Long id,
            @RequestParam EstadoCalificacion estado) {
        return ResponseEntity.ok(calificacionService.moderar(id, estado));
    }
}