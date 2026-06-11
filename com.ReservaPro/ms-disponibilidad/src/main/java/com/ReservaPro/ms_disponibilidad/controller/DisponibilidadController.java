package com.ReservaPro.ms_disponibilidad.controller;

import com.ReservaPro.ms_disponibilidad.client.ReservaClient;
import com.ReservaPro.ms_disponibilidad.dto.request.DisponibilidadRequest;
import com.ReservaPro.ms_disponibilidad.dto.response.DisponibilidadResponse;
import com.ReservaPro.ms_disponibilidad.mapper.DisponibilidadMapper;
import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;
import com.ReservaPro.ms_disponibilidad.service.DisponibilidadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

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
        description = "Operaciones relacionadas con la disponibilidad"
)
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;
    private final ReservaClient reservaClient;

    @GetMapping
    @Operation(
            summary = "Obtener todas las disponibilidades",
            description = "Retorna una lista de todas las disponibilidades"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    public ResponseEntity<List<DisponibilidadResponse>> listarDisponibilidades() {

        List<DisponibilidadResponse> respuesta =
                disponibilidadService.listarDisponibilidades()
                        .stream()
                        .map(DisponibilidadMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    @Operation(
            summary = "Crear disponibilidad",
            description = "Crea una nueva disponibilidad"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disponibilidad creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<DisponibilidadResponse> guardarDisponibilidad(

            @RequestBody(
                    description = "Datos de la disponibilidad a crear",
                    required = true
            )
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            DisponibilidadRequest request
    ) {

        Disponibilidad disponibilidad =
                DisponibilidadMapper.toEntity(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        DisponibilidadMapper.toResponse(
                                disponibilidadService.guardarDisponibilidad(disponibilidad)
                        )
                );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar disponibilidad por ID",
            description = "Obtiene una disponibilidad según su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidad encontrada"),
            @ApiResponse(responseCode = "404", description = "Disponibilidad no encontrada")
    })
    public ResponseEntity<DisponibilidadResponse> buscarPorId(

            @Parameter(
                    description = "ID de la disponibilidad",
                    required = true
            )
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                DisponibilidadMapper.toResponse(
                        disponibilidadService.buscarPorId(id)
                )
        );
    }

    @GetMapping("/fecha/{fecha}")
    @Operation(
            summary = "Buscar disponibilidades por fecha",
            description = "Retorna las disponibilidades asociadas a una fecha"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    })
    public ResponseEntity<List<DisponibilidadResponse>> buscarPorFecha(

            @Parameter(
                    description = "Fecha en formato YYYY-MM-DD",
                    example = "2026-06-10",
                    required = true
            )
            @PathVariable LocalDate fecha
    ) {

        return ResponseEntity.ok(
                disponibilidadService.buscarPorFecha(fecha)
                        .stream()
                        .map(DisponibilidadMapper::toResponse)
                        .toList()
        );
    }

    @GetMapping("/activas")
    @Operation(
            summary = "Obtener disponibilidades activas",
            description = "Retorna todas las disponibilidades activas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente")
    })
    public ResponseEntity<List<DisponibilidadResponse>> buscarActivas() {

        return ResponseEntity.ok(
                disponibilidadService.buscarActivas()
                        .stream()
                        .map(DisponibilidadMapper::toResponse)
                        .toList()
        );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar disponibilidad",
            description = "Actualiza una disponibilidad existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidad actualizada"),
            @ApiResponse(responseCode = "404", description = "Disponibilidad no encontrada")
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
            DisponibilidadRequest request
    ) {

        Disponibilidad disponibilidad =
                DisponibilidadMapper.toEntity(request);

        return ResponseEntity.ok(
                DisponibilidadMapper.toResponse(
                        disponibilidadService.actualizarDisponibilidad(
                                id,
                                disponibilidad
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar disponibilidad",
            description = "Elimina una disponibilidad según su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disponibilidad eliminada"),
            @ApiResponse(responseCode = "404", description = "Disponibilidad no encontrada")
    })
    public ResponseEntity<Void> eliminarDisponibilidad(

            @Parameter(
                    description = "ID de la disponibilidad",
                    required = true
            )
            @PathVariable Long id
    ) {

        disponibilidadService.eliminarDisponibilidad(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reserva/{idReserva}")
    @Operation(
            summary = "Obtener reserva asociada",
            description = "Consulta información de una reserva mediante Feign Client"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<Object> obtenerReservaDesdeDisponibilidad(

            @Parameter(
                    description = "ID de la reserva",
                    required = true
            )
            @PathVariable Long idReserva
    ) {

        return ResponseEntity.ok(
                reservaClient.obtenerReservaPorId(idReserva)
        );
    }
}