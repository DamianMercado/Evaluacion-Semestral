package com.ReservaPro.ms_gestion_servicio.controller;

import com.ReservaPro.ms_gestion_servicio.dto.request.GestionServicioRequest;
import com.ReservaPro.ms_gestion_servicio.dto.response.GestionServicioResponse;
import com.ReservaPro.ms_gestion_servicio.enums.EstadoServicio;
import com.ReservaPro.ms_gestion_servicio.service.GestionServicioService;
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
@RequestMapping("/api/v1/gestion-servicio")
@RequiredArgsConstructor
@Tag(name = "Gestión de Servicios", description = "Operaciones relacionadas con la gestión de servicios")
public class GestionServicioController {

    private final GestionServicioService gestionServicioService;

    @GetMapping
    @Operation(summary = "Obtener todos los servicios")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<GestionServicioResponse>> obtenerTodos() {
        return ResponseEntity.ok(gestionServicioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener servicio por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio encontrado"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    public ResponseEntity<GestionServicioResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(gestionServicioService.obtenerPorId(id));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener servicios por estado")
    public ResponseEntity<List<GestionServicioResponse>> obtenerPorEstado(@PathVariable EstadoServicio estado) {
        return ResponseEntity.ok(gestionServicioService.obtenerPorEstado(estado));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar servicios por nombre")
    public ResponseEntity<List<GestionServicioResponse>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(gestionServicioService.buscarPorNombre(nombre));
    }

    @GetMapping("/proveedor/{proveedorId}")
    @Operation(summary = "Obtener servicios por proveedor")
    public ResponseEntity<List<GestionServicioResponse>> obtenerPorProveedor(@PathVariable Long proveedorId) {
        return ResponseEntity.ok(gestionServicioService.obtenerPorProveedor(proveedorId));
    }

    @PostMapping
    @Operation(summary = "Crear servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Servicio creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<GestionServicioResponse> crear(@Valid @RequestBody GestionServicioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gestionServicioService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio actualizado"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    public ResponseEntity<GestionServicioResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody GestionServicioRequest request) {
        return ResponseEntity.ok(gestionServicioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar servicio (cambia estado a ELIMINADO)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Servicio eliminado"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gestionServicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado del servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado cambiado correctamente"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    public ResponseEntity<GestionServicioResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoServicio estado) {
        return ResponseEntity.ok(gestionServicioService.cambiarEstado(id, estado));
    }
}