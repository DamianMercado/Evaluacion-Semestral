package com.ReservaPro.ms_disponibilidad.controller;

import com.ReservaPro.ms_disponibilidad.client.ReservaClient;
import com.ReservaPro.ms_disponibilidad.dto.request.DisponibilidadRequest;
import com.ReservaPro.ms_disponibilidad.dto.response.DisponibilidadResponse;
import com.ReservaPro.ms_disponibilidad.mapper.DisponibilidadMapper;
import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;
import com.ReservaPro.ms_disponibilidad.service.DisponibilidadService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/disponibilidades")
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;
    private final ReservaClient reservaClient;

    public DisponibilidadController(
            DisponibilidadService disponibilidadService,
            ReservaClient reservaClient
    ) {
        this.disponibilidadService = disponibilidadService;
        this.reservaClient = reservaClient;
    }

    @GetMapping
    public List<DisponibilidadResponse> listarDisponibilidades() {
        return disponibilidadService.listarDisponibilidades()
                .stream()
                .map(DisponibilidadMapper::toResponse)
                .toList();
    }

    @PostMapping
    public DisponibilidadResponse guardarDisponibilidad(
            @Valid @RequestBody DisponibilidadRequest request
    ) {
        Disponibilidad disponibilidad = DisponibilidadMapper.toEntity(request);
        return DisponibilidadMapper.toResponse(
                disponibilidadService.guardarDisponibilidad(disponibilidad)
        );
    }

    @GetMapping("/{id}")
    public DisponibilidadResponse buscarPorId(@PathVariable Long id) {
        return DisponibilidadMapper.toResponse(
                disponibilidadService.buscarPorId(id)
        );
    }

    @GetMapping("/fecha/{fecha}")
    public List<DisponibilidadResponse> buscarPorFecha(@PathVariable LocalDate fecha) {
        return disponibilidadService.buscarPorFecha(fecha)
                .stream()
                .map(DisponibilidadMapper::toResponse)
                .toList();
    }

    @GetMapping("/activas")
    public List<DisponibilidadResponse> buscarActivas() {
        return disponibilidadService.buscarActivas()
                .stream()
                .map(DisponibilidadMapper::toResponse)
                .toList();
    }

    @PutMapping("/{id}")
    public DisponibilidadResponse actualizarDisponibilidad(
            @PathVariable Long id,
            @Valid @RequestBody DisponibilidadRequest request
    ) {
        Disponibilidad disponibilidad = DisponibilidadMapper.toEntity(request);
        return DisponibilidadMapper.toResponse(
                disponibilidadService.actualizarDisponibilidad(id, disponibilidad)
        );
    }

    @DeleteMapping("/{id}")
    public void eliminarDisponibilidad(@PathVariable Long id) {
        disponibilidadService.eliminarDisponibilidad(id);
    }

    @GetMapping("/reserva/{idReserva}")
    public Object obtenerReservaDesdeDisponibilidad(@PathVariable Long idReserva) {
        return reservaClient.obtenerReservaPorId(idReserva);
    }
}