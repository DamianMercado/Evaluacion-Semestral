package com.ReservaPro.ms_disponibilidad.service;

import com.ReservaPro.ms_disponibilidad.dto.request.DisponibilidadRequest;
import com.ReservaPro.ms_disponibilidad.dto.response.DisponibilidadResponse;
import com.ReservaPro.ms_disponibilidad.exception.DisponibilidadNotFoundException;
import com.ReservaPro.ms_disponibilidad.mapper.DisponibilidadMapper;
import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;
import com.ReservaPro.ms_disponibilidad.repository.DisponibilidadRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepository;

    private static final Logger log =
            LoggerFactory.getLogger(DisponibilidadService.class);

    public List<DisponibilidadResponse> listarDisponibilidades() {

        log.info("Listando todas las disponibilidades");

        return disponibilidadRepository.findAll()
                .stream()
                .map(DisponibilidadMapper::toResponse)
                .toList();
    }

    public DisponibilidadResponse guardarDisponibilidad(
            DisponibilidadRequest request) {

        log.info(
                "Creando disponibilidad para la fecha {}",
                request.getFecha()
        );

        Disponibilidad disponibilidad =
                DisponibilidadMapper.toEntity(request);

        Disponibilidad guardada =
                disponibilidadRepository.save(disponibilidad);

        log.info(
                "Disponibilidad creada con ID {}",
                guardada.getIdDisponibilidad()
        );

        return DisponibilidadMapper.toResponse(guardada);
    }

    public DisponibilidadResponse buscarPorId(Long id) {

        log.info("Buscando disponibilidad con ID {}", id);

        Disponibilidad disponibilidad =
                disponibilidadRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error(
                                    "No existe disponibilidad con ID {}",
                                    id
                            );
                            return new DisponibilidadNotFoundException(id);
                        });

        return DisponibilidadMapper.toResponse(disponibilidad);
    }

    public List<DisponibilidadResponse> buscarPorFecha(
            LocalDate fecha) {

        log.info(
                "Buscando disponibilidades para la fecha {}",
                fecha
        );

        return disponibilidadRepository.findByFecha(fecha)
                .stream()
                .map(DisponibilidadMapper::toResponse)
                .toList();
    }

    public List<DisponibilidadResponse> buscarActivas() {

        log.info("Buscando disponibilidades activas");

        return disponibilidadRepository.findByActivo(true)
                .stream()
                .map(DisponibilidadMapper::toResponse)
                .toList();
    }

    public DisponibilidadResponse actualizarDisponibilidad(
            Long id,
            DisponibilidadRequest request) {

        log.info(
                "Actualizando disponibilidad con ID {}",
                id
        );

        Disponibilidad disponibilidad =
                disponibilidadRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error(
                                    "No existe disponibilidad con ID {}",
                                    id
                            );
                            return new DisponibilidadNotFoundException(id);
                        });

        disponibilidad.setFecha(request.getFecha());
        disponibilidad.setHoraInicio(request.getHoraInicio());
        disponibilidad.setHoraFin(request.getHoraFin());
        disponibilidad.setCuposDisponibles(
                request.getCuposDisponibles());
        disponibilidad.setCuposTotales(
                request.getCuposTotales());
        disponibilidad.setEstado(request.getEstado());
        disponibilidad.setObservacion(
                request.getObservacion());
        disponibilidad.setActivo(request.getActivo());

        Disponibilidad actualizada =
                disponibilidadRepository.save(disponibilidad);

        log.info(
                "Disponibilidad con ID {} actualizada correctamente",
                id
        );

        return DisponibilidadMapper.toResponse(actualizada);
    }

    public void eliminarDisponibilidad(Long id) {

        log.info(
                "Eliminando disponibilidad con ID {}",
                id
        );

        Disponibilidad disponibilidad =
                disponibilidadRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error(
                                    "No existe disponibilidad con ID {}",
                                    id
                            );
                            return new DisponibilidadNotFoundException(id);
                        });

        disponibilidadRepository.delete(disponibilidad);

        log.info(
                "Disponibilidad con ID {} eliminada correctamente",
                id
        );
    }
}