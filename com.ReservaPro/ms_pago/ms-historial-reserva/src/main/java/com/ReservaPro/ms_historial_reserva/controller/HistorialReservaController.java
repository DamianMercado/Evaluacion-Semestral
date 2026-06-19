package com.ReservaPro.ms_historial_reserva.controller;

import com.ReservaPro.ms_historial_reserva.dto.request.HistorialReservaRequest;
import com.ReservaPro.ms_historial_reserva.dto.response.HistorialReservaResponse;
import com.ReservaPro.ms_historial_reserva.service.HistorialReservaService;

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
@RequestMapping("/api/v1/historial-reservas")
@RequiredArgsConstructor
@Tag(
        name = "Historial de Reservas",
        description = "Operaciones relacionadas con el historial de reservas"
)
public class HistorialReservaController {

    private final HistorialReservaService historialReservaService;

    @GetMapping
    @Operation(
            summary = "Obtener todos los historiales",
            description = "Retorna una lista de todos los historiales de reservas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista obtenida correctamente")
    })
    public ResponseEntity<List<HistorialReservaResponse>> obtenerHistoriales() {

        return ResponseEntity.ok(
                historialReservaService.obtener()
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener historial por ID",
            description = "Obtiene un historial según su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Historial encontrado"),
            @ApiResponse(responseCode = "404",
                    description = "Historial no encontrado")
    })
    public ResponseEntity<HistorialReservaResponse> obtenerHistorial(

            @Parameter(
                    description = "ID del historial",
                    required = true
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                historialReservaService.obtenerPorId(id)
        );
    }

    @PostMapping
    @Operation(
            summary = "Crear un historial",
            description = "Crea un nuevo historial de reserva"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Historial creado correctamente"),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos")
    })
    public ResponseEntity<HistorialReservaResponse> crearHistorial(

            @RequestBody(
                    description = "Datos del historial a crear",
                    required = true
            )
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            HistorialReservaRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(historialReservaService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un historial",
            description = "Actualiza un historial existente por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Historial actualizado"),
            @ApiResponse(responseCode = "404",
                    description = "Historial no encontrado")
    })
    public ResponseEntity<HistorialReservaResponse> actualizarHistorial(

            @Parameter(
                    description = "ID del historial",
                    required = true
            )
            @PathVariable Long id,

            @RequestBody(
                    description = "Datos actualizados del historial",
                    required = true
            )
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            HistorialReservaRequest request) {

        return ResponseEntity.ok(
                historialReservaService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un historial",
            description = "Elimina un historial por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Historial eliminado"),
            @ApiResponse(responseCode = "404",
                    description = "Historial no encontrado")
    })
    public ResponseEntity<Void> eliminarHistorial(

            @Parameter(
                    description = "ID del historial",
                    required = true
            )
            @PathVariable Long id) {

        historialReservaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}