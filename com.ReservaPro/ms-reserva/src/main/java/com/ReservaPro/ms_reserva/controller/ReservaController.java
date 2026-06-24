package com.ReservaPro.ms_reserva.controller;

import com.ReservaPro.ms_reserva.dto.request.ReservaPagoRequest;
import com.ReservaPro.ms_reserva.dto.request.ReservaRequest;
import com.ReservaPro.ms_reserva.dto.response.ReservaCompletaResponse;
import com.ReservaPro.ms_reserva.dto.response.ReservaResponse;
import com.ReservaPro.ms_reserva.enums.EstadoReserva;
import com.ReservaPro.ms_reserva.service.ReservaService;
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
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Operaciones relacionadas con las reservas")
public class ReservaController {

    private final ReservaService reservaService;
    //CRUD BÁSICO

        @GetMapping
        @Operation(summary = "Obtener todas las reservas")
        public ResponseEntity<List<ReservaResponse>> obtenerTodos() {
            return ResponseEntity.ok(reservaService.obtenerTodos());
        }

        @GetMapping("/{id}")
        @Operation(summary = "Obtener reserva por ID")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
                @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
        })
        public ResponseEntity<ReservaResponse> obtenerPorId(@PathVariable Long id) {
            return ResponseEntity.ok(reservaService.obtenerPorId(id));
        }

        @GetMapping("/{id}/completa")
        @Operation(summary = "Obtener reserva completa con datos de otros MS")
        public ResponseEntity<ReservaCompletaResponse> obtenerReservaCompleta(@PathVariable Long id) {
            return ResponseEntity.ok(reservaService.obtenerReservaCompleta(id));
        }

        @PostMapping
        @Operation(summary = "Crear una nueva reserva (estado PENDIENTE_PAGO)")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
                @ApiResponse(responseCode = "400", description = "Datos inválidos")
        })
        public ResponseEntity<ReservaResponse> crear(@Valid @RequestBody ReservaRequest request) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(reservaService.crear(request));
        }

        @PutMapping("/{id}")
        @Operation(summary = "Actualizar reserva (solo si está PENDIENTE_PAGO)")
        public ResponseEntity<ReservaResponse> actualizar(
                @PathVariable Long id,
                @Valid @RequestBody ReservaRequest request) {
            return ResponseEntity.ok(reservaService.actualizar(id, request));
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar reserva")
        public ResponseEntity<Void> eliminar(@PathVariable Long id) {
            reservaService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
    //

    //FLUJO DE PAGO

        @PatchMapping("/{id}/pagar")
        @Operation(summary = "Confirmar pago de una reserva (cambia a PAGADO)")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Pago confirmado"),
                @ApiResponse(responseCode = "400", description = "Estado inválido"),
                @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
        })
        public ResponseEntity<ReservaResponse> confirmarPago(
                @PathVariable Long id,
                @Valid @RequestBody ReservaPagoRequest request) {
            return ResponseEntity.ok(reservaService.confirmarPago(id, request));
        }

        @PatchMapping("/{id}/confirmar")
        @Operation(summary = "Confirmar reserva (cambia a CONFIRMADA)")
        public ResponseEntity<ReservaResponse> confirmarReserva(@PathVariable Long id) {
            return ResponseEntity.ok(reservaService.confirmarReserva(id));
        }

        @PatchMapping("/{id}/cancelar")
        @Operation(summary = "Cancelar reserva (cambia a CANCELADA)")
        public ResponseEntity<ReservaResponse> cancelarReserva(@PathVariable Long id) {
            return ResponseEntity.ok(reservaService.cancelarReserva(id));
        }

        @PatchMapping("/{id}/completar")
        @Operation(summary = "Completar reserva (cambia a COMPLETADA)")
        public ResponseEntity<ReservaResponse> completarReserva(@PathVariable Long id) {
            return ResponseEntity.ok(reservaService.completarReserva(id));
        }
    //

    //CONSULTAS

        @GetMapping("/usuario/{idUsuario}")
        @Operation(summary = "Obtener reservas por usuario")
        public ResponseEntity<List<ReservaResponse>> obtenerPorUsuario(@PathVariable Long idUsuario) {
            return ResponseEntity.ok(reservaService.obtenerPorUsuario(idUsuario));
        }

        @GetMapping("/estado/{estado}")
        @Operation(summary = "Obtener reservas por estado")
        public ResponseEntity<List<ReservaResponse>> obtenerPorEstado(@PathVariable EstadoReserva estado) {
            return ResponseEntity.ok(reservaService.obtenerPorEstado(estado));
        }
    //
}