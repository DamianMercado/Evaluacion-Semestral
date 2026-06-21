package com.ReservaPro.ms_cancelacion.controller;

import com.ReservaPro.ms_cancelacion.dto.request.CancelacionRequest;
import com.ReservaPro.ms_cancelacion.dto.response.CancelacionResponse;
import com.ReservaPro.ms_cancelacion.service.CancelacionService;

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
@RequestMapping("/api/v1/cancelaciones")
@RequiredArgsConstructor
@Tag(
        name = "Cancelaciones",
        description = "Operaciones relacionadas con las cancelaciones"
)
public class CancelacionController {

    private final CancelacionService cancelacionService;

    @GetMapping
    @Operation(
            summary = "Obtener todas las cancelaciones",
            description = "Retorna una lista de todas las cancelaciones"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista obtenida correctamente"
            )
    })
    public ResponseEntity<List<CancelacionResponse>> obtenerCancelaciones() {

        return ResponseEntity.ok(
                cancelacionService.obtener()
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener cancelación por ID",
            description = "Obtiene una cancelación según su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cancelación encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cancelación no encontrada"
            )
    })
    public ResponseEntity<CancelacionResponse> obtenerCancelacion(

            @Parameter(
                    description = "ID de la cancelación",
                    required = true
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                cancelacionService.obtenerPorId(id)
        );
    }

    @PostMapping
    @Operation(
            summary = "Crear una cancelación",
            description = "Crea una nueva cancelación en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cancelación creada correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos"
            )
    })
    public ResponseEntity<CancelacionResponse> crearCancelacion(

            @RequestBody(
                    description = "Datos de la cancelación a crear",
                    required = true
            )
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            CancelacionRequest cancelacion) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cancelacionService.crear(cancelacion));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una cancelación",
            description = "Actualiza una cancelación existente por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cancelación actualizada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cancelación no encontrada"
            )
    })
    public ResponseEntity<CancelacionResponse> actualizarCancelacion(

            @Parameter(
                    description = "ID de la cancelación",
                    required = true
            )
            @PathVariable Long id,

            @RequestBody(
                    description = "Datos actualizados de la cancelación",
                    required = true
            )
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            CancelacionRequest cancelacion) {

        return ResponseEntity.ok(
                cancelacionService.actualizar(id, cancelacion)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una cancelación",
            description = "Elimina una cancelación por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Cancelación eliminada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cancelación no encontrada"
            )
    })
    public ResponseEntity<Void> eliminarCancelacion(

            @Parameter(
                    description = "ID de la cancelación",
                    required = true
            )
            @PathVariable Long id) {

        cancelacionService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}