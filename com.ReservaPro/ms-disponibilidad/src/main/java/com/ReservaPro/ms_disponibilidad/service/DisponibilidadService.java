package com.ReservaPro.ms_disponibilidad.service;

import com.ReservaPro.ms_disponibilidad.client.ReservaClient;
import com.ReservaPro.ms_disponibilidad.dto.request.DisponibilidadRequest;
import com.ReservaPro.ms_disponibilidad.dto.response.DisponibilidadResponse;
import com.ReservaPro.ms_disponibilidad.exception.DisponibilidadNotFoundException;
import com.ReservaPro.ms_disponibilidad.exception.ReglaNegocioException;
import com.ReservaPro.ms_disponibilidad.mapper.DisponibilidadMapper;
import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;
import com.ReservaPro.ms_disponibilidad.model.EstadoDisponibilidad;
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

    private static final Logger log =
            LoggerFactory.getLogger(DisponibilidadService.class);

    private final DisponibilidadRepository disponibilidadRepository;
    private final DisponibilidadMapper disponibilidadMapper;
    private final ReservaClient reservaClient;

    public List<DisponibilidadResponse> obtener() {

        log.info("Listando todas las disponibilidades");

        return disponibilidadMapper.toResponseList(
                disponibilidadRepository.findAll()
        );
    }

    public DisponibilidadResponse obtenerPorId(Long id) {

        log.info("Buscando disponibilidad con ID {}", id);

        return disponibilidadMapper.toResponse(
                disponibilidadRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new DisponibilidadNotFoundException(id)
                        )
        );
    }

    public DisponibilidadResponse crear(
            DisponibilidadRequest request) {

        log.info(
                "Creando disponibilidad para la fecha {}",
                request.getFecha()
        );

        validarDisponibilidad(request);

        Disponibilidad disponibilidad =
                disponibilidadMapper.toEntity(request);

        return disponibilidadMapper.toResponse(
                disponibilidadRepository.save(disponibilidad)
        );
    }

    public DisponibilidadResponse actualizar(
            Long id,
            DisponibilidadRequest request) {

        log.info("Actualizando disponibilidad con ID {}", id);

        Disponibilidad disponibilidadExistente =
                disponibilidadRepository.findById(id)
                        .orElseThrow(() ->
                                new DisponibilidadNotFoundException(id)
                        );

        validarDisponibilidad(request);

        disponibilidadExistente.setIdServicio(request.getIdServicio());
        disponibilidadExistente.setFecha(request.getFecha());
        disponibilidadExistente.setHoraInicio(request.getHoraInicio());
        disponibilidadExistente.setHoraFin(request.getHoraFin());
        disponibilidadExistente.setCuposDisponibles(request.getCuposDisponibles());
        disponibilidadExistente.setCuposTotales(request.getCuposTotales());
        disponibilidadExistente.setEstado(request.getEstado());
        disponibilidadExistente.setObservacion(request.getObservacion());
        disponibilidadExistente.setActivo(request.getActivo());

        return disponibilidadMapper.toResponse(
                disponibilidadRepository.save(disponibilidadExistente)
        );
    }

    public void eliminar(Long id) {

        log.info("Eliminando disponibilidad con ID {}", id);

        if (!disponibilidadRepository.existsById(id)) {
            throw new DisponibilidadNotFoundException(id);
        }

        disponibilidadRepository.deleteById(id);
    }

    public List<DisponibilidadResponse> obtenerPorFecha(
            LocalDate fecha) {

        log.info(
                "Buscando disponibilidades para la fecha {}",
                fecha
        );

        return disponibilidadMapper.toResponseList(
                disponibilidadRepository.findByFecha(fecha)
        );
    }

    public List<DisponibilidadResponse> obtenerActivas() {

        log.info("Buscando disponibilidades activas");

        return disponibilidadMapper.toResponseList(
                disponibilidadRepository.findByActivo(true)
        );
    }

    public List<DisponibilidadResponse> obtenerPorEstado(
            EstadoDisponibilidad estado) {

        log.info(
                "Buscando disponibilidades con estado {}",
                estado
        );

        return disponibilidadMapper.toResponseList(
                disponibilidadRepository.findByEstado(estado)
        );
    }

    public Object obtenerReservaPorId(Long idReserva) {

        log.info(
                "Consultando reserva ID {} desde ms-reserva",
                idReserva
        );

        return reservaClient.obtenerReservaPorId(idReserva);
    }

    private void validarDisponibilidad(
            DisponibilidadRequest request) {

        if (request.getHoraInicio()
                .isAfter(request.getHoraFin())) {

            throw new ReglaNegocioException(
                    "La hora de inicio no puede ser posterior a la hora de fin"
            );
        }

        if (request.getHoraInicio()
                .equals(request.getHoraFin())) {

            throw new ReglaNegocioException(
                    "La hora de inicio y la hora de fin no pueden ser iguales"
            );
        }

        if (request.getCuposDisponibles()
                > request.getCuposTotales()) {

            throw new ReglaNegocioException(
                    "Los cupos disponibles no pueden ser mayores que los cupos totales"
            );
        }
    }
}