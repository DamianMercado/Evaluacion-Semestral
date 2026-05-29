package com.ReservaPro.ms_cancelacion.controller;

import com.ReservaPro.ms_cancelacion.dto.request.CancelacionRequest;
import com.ReservaPro.ms_cancelacion.dto.response.CancelacionResponse;
import com.ReservaPro.ms_cancelacion.service.CancelacionService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cancelaciones")
@RequiredArgsConstructor
public class CancelacionController {

    private final CancelacionService cancelacionService;

    @GetMapping
    public ResponseEntity<List<CancelacionResponse>> obtenerCancelaciones() {

        return ResponseEntity.ok()
                .body(cancelacionService.obtener());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CancelacionResponse> obtenerCancelacion(
            @PathVariable Long id) {

        return ResponseEntity.ok()
                .body(cancelacionService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CancelacionResponse> crearCancelacion(
            @Valid @RequestBody CancelacionRequest cancelacion) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cancelacionService.crear(cancelacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CancelacionResponse> actualizarCancelacion(
            @PathVariable Long id,
            @RequestBody CancelacionRequest cancelacion) {

        return ResponseEntity.ok()
                .body(cancelacionService.actualizar(id, cancelacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCancelacion(
            @PathVariable Long id) {

        cancelacionService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}