package com.ReservaPro.ms_pago.controller;

import com.ReservaPro.ms_pago.dto.request.PagoRequest;
import com.ReservaPro.ms_pago.dto.response.EstadoResponse;
import com.ReservaPro.ms_pago.dto.response.MontoResponse;
import com.ReservaPro.ms_pago.dto.response.PagoResponse;
import com.ReservaPro.ms_pago.model.Pago;
import com.ReservaPro.ms_pago.service.PagoService;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Operaciones relacionadas con los pagos")
public class PagoController {

    private final PagoService pagoService;

    @GetMapping
    @Operation(summary = "Obtener todos los pagos")
    public ResponseEntity<List<Pago>> listarPagos() {
        return ResponseEntity.ok(pagoService.listarPagos());
    }

    @GetMapping("/reserva/{idReserva}")
    @Operation(summary = "Obtener pagos por reserva")
    public ResponseEntity<List<Pago>> obtenerPagosPorReserva(@PathVariable Long idReserva) {
        return ResponseEntity.ok(pagoService.obtenerPagosPorReserva(idReserva));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pago (estado PENDIENTE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<PagoResponse> procesarPago(@Valid @RequestBody PagoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pagoService.procesarPago(request));
    }

    @PatchMapping("/validar/{id}")
    @Operation(summary = "Validar pago (cambia a PAGADO y notifica a ms-reserva)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago validado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PagoResponse> validarPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.validarPago(id));
    }

    @PatchMapping("/reembolsar/{id}")
    @Operation(summary = "Reembolsar pago (cambia a REEMBOLSO)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago reembolsado"),
            @ApiResponse(responseCode = "400", description = "No se puede reembolsar"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PagoResponse> reembolsarPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.reembolsarPago(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pago (solo si está PENDIENTE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pago eliminado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estado/{id}")
    @Operation(summary = "Obtener estado del pago")
    public ResponseEntity<EstadoResponse> verEstadoPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.verEstadoPago(id));
    }

    @GetMapping("/monto/{id}")
    @Operation(summary = "Obtener monto del pago")
    public ResponseEntity<MontoResponse> verMontoPago(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.verMontoPago(id));
    }
}