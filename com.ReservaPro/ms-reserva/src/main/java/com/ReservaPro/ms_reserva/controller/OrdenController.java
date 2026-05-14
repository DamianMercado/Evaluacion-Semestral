package com.ReservaPro.ms_reserva.controller;
import com.ReservaPro.ms_reserva.client.OrdenResponse;
import com.ReservaPro.ms_reserva.client.UsuariosClient;
import com.ReservaPro.ms_reserva.model.Orden;
import com.ReservaPro.ms_reserva.model.Usuario;
import com.ReservaPro.ms_reserva.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ordenes")
public class OrdenController {
    private final OrdenService ordenService;
    private final UsuariosClient usuariosClient;

    @GetMapping
    public ResponseEntity<List<Orden>> findAll() {
        return ResponseEntity.ok(ordenService.obtenerUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenResponse> findById(@PathVariable Long id) {
        Usuario user = usuariosClient.getUsuario(id);
        Orden order = ordenService.obtenerUsuario(id);
        OrdenResponse ordenResponse = new OrdenResponse();
        return ResponseEntity.ok(new OrdenResponse(order.getId(), user, order.getTotal(), order.getFechaCreacion(), order.getEstado()));
    }

    @PostMapping
    public ResponseEntity<Orden> create(@RequestBody Orden orden) {
        Orden ordenCreado = ordenService.crearUsuario(orden);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenCreado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        ordenService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
