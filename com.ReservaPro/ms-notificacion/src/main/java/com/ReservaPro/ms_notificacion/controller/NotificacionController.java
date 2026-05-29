package com.ReservaPro.ms_notificacion.controller;

import com.ReservaPro.ms_notificacion.dto.request.NotificacionRequest;
import com.ReservaPro.ms_notificacion.dto.response.NotificacionResponse;
import com.ReservaPro.ms_notificacion.service.NotificacionService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor

public class NotificacionController {

    private final NotificacionService notificacionService;





    @GetMapping
    public ResponseEntity<List<NotificacionResponse>> obtenerNotificaciones() {

        return ResponseEntity.ok(
                notificacionService.obtener()
        );
    }





    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponse> obtenerNotificacion(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                notificacionService.obtenerPorId(id)
        );
    }





    @PostMapping
    public ResponseEntity<NotificacionResponse> crearNotificacion(
            @Valid @RequestBody NotificacionRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificacionService.crear(request));
    }





    @PutMapping("/{id}")
    public ResponseEntity<NotificacionResponse> actualizarNotificacion(
            @PathVariable Long id,
            @Valid @RequestBody NotificacionRequest request) {

        return ResponseEntity.ok(
                notificacionService.actualizar(id, request)
        );
    }





    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(
            @PathVariable Long id) {

        notificacionService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
