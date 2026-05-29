package com.ReservaPro.ms_reserva.controller;
import com.ReservaPro.ms_reserva.client.UsuarioClient;
import com.ReservaPro.ms_reserva.model.Reserva;
import com.ReservaPro.ms_reserva.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ordenes")
public class ReservaController {
    private final ReservaService ordenService;
    private final UsuarioClient usuariosClient;

    @GetMapping
    public ResponseEntity<List<Reserva>> findAll() {
        return ResponseEntity.ok(ordenService.obtenerUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> findById(@PathVariable Long id) {
        Usuario user = usuariosClient.getUsuario(id);
        Reserva order = ordenService.obtenerUsuario(id);
        ReservaResponse ordenResponse = new ReservaResponse();
        return ResponseEntity.ok(new ReservaResponse(order.getId(), user, order.getTotal(), order.getFechaCreacion(), order.getEstado()));
    }

    @PostMapping
    public ResponseEntity<Reserva> create(@RequestBody Reserva orden) {
        Reserva ordenCreado = ordenService.crearUsuario(orden);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenCreado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        ordenService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
