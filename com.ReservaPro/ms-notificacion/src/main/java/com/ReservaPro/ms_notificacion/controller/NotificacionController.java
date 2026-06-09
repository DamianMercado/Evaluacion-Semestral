package com.ReservaPro.ms_notificacion.controller;

import com.ReservaPro.ms_notificacion.dto.request.NotificacionRequest;
import com.ReservaPro.ms_notificacion.dto.response.NotificacionResponse;
import com.ReservaPro.ms_notificacion.service.NotificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
@Tag(
        name = "Notificaciones",
        description = "Operaciones relacionadas con las notificaciones"
)
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping
    @Operation(
            summary = "Obtener todas las notificaciones",
            description = "Retorna una lista de todas las notificaciones"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    public ResponseEntity<List<NotificacionResponse>> obtenerNotificaciones() {

        return ResponseEntity.ok(
                notificacionService.obtener()
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener notificación por ID",
            description = "Obtiene una notificación según su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<NotificacionResponse> obtenerNotificacion(

            @Parameter(
                    description = "ID de la notificación",
                    required = true
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                notificacionService.obtenerPorId(id)
        );
    }

    @PostMapping
    @Operation(
            summary = "Crear una notificación",
            description = "Crea una nueva notificación en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<NotificacionResponse> crearNotificacion(

            @RequestBody(
                    description = "Datos de la notificación a crear",
                    required = true
            )
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            NotificacionRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificacionService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una notificación",
            description = "Actualiza una notificación existente por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación actualizada"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<NotificacionResponse> actualizarNotificacion(

            @Parameter(
                    description = "ID de la notificación",
                    required = true
            )
            @PathVariable Long id,

            @RequestBody(
                    description = "Datos actualizados de la notificación",
                    required = true
            )
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            NotificacionRequest request) {

        return ResponseEntity.ok(
                notificacionService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una notificación",
            description = "Elimina una notificación por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notificación eliminada"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<Void> eliminarNotificacion(

            @Parameter(
                    description = "ID de la notificación",
                    required = true
            )
            @PathVariable Long id) {

        notificacionService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}