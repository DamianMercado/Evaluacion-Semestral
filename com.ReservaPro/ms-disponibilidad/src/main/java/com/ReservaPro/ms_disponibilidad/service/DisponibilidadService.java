package com.ReservaPro.ms_disponibilidad.service;

import com.ReservaPro.ms_disponibilidad.exception.DisponibilidadNotFoundException;
import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;
import com.ReservaPro.ms_disponibilidad.repository.DisponibilidadRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepository;

    public DisponibilidadService(DisponibilidadRepository disponibilidadRepository) {
        this.disponibilidadRepository = disponibilidadRepository;
    }

    public List<Disponibilidad> listarDisponibilidades() {
        return disponibilidadRepository.findAll();
    }

    public Disponibilidad guardarDisponibilidad(Disponibilidad disponibilidad) {
        return disponibilidadRepository.save(disponibilidad);
    }

    public Disponibilidad buscarPorId(Long id) {
        return disponibilidadRepository.findById(id)
                .orElseThrow(() -> new DisponibilidadNotFoundException(id));
    }

    public List<Disponibilidad> buscarPorFecha(LocalDate fecha) {
        return disponibilidadRepository.findByFecha(fecha);
    }

    public List<Disponibilidad> buscarActivas() {
        return disponibilidadRepository.findByActivo(true);
    }

    public Disponibilidad actualizarDisponibilidad(Long id, Disponibilidad nuevaDisponibilidad) {
        Disponibilidad disponibilidad = buscarPorId(id);

        disponibilidad.setFecha(nuevaDisponibilidad.getFecha());
        disponibilidad.setHoraInicio(nuevaDisponibilidad.getHoraInicio());
        disponibilidad.setHoraFin(nuevaDisponibilidad.getHoraFin());
        disponibilidad.setCuposDisponibles(nuevaDisponibilidad.getCuposDisponibles());
        disponibilidad.setCuposTotales(nuevaDisponibilidad.getCuposTotales());
        disponibilidad.setEstado(nuevaDisponibilidad.getEstado());
        disponibilidad.setObservacion(nuevaDisponibilidad.getObservacion());
        disponibilidad.setActivo(nuevaDisponibilidad.getActivo());

        return disponibilidadRepository.save(disponibilidad);
    }

    public void eliminarDisponibilidad(Long id) {
        Disponibilidad disponibilidad = buscarPorId(id);
        disponibilidadRepository.delete(disponibilidad);
    }
}