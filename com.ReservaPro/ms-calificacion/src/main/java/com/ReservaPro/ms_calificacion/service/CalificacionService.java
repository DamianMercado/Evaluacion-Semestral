package com.ReservaPro.ms_calificacion.service;

import com.ReservaPro.ms_calificacion.dto.request.CalificacionRequest;
import com.ReservaPro.ms_calificacion.dto.response.CalificacionResponse;
import com.ReservaPro.ms_calificacion.enums.EstadoCalificacion;
import com.ReservaPro.ms_calificacion.exception.CalificacionNoEncontradaException;
import com.ReservaPro.ms_calificacion.mapper.CalificacionMapper;
import com.ReservaPro.ms_calificacion.model.Calificacion;
import com.ReservaPro.ms_calificacion.repository.CalificacionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalificacionService {

    private static final Logger log = LoggerFactory.getLogger(CalificacionService.class);

    private final CalificacionRepository calificacionRepository;
    private final CalificacionMapper calificacionMapper;
    private final DiscoveryClient discoveryClient;

    public String getServiceInfo() {
        List<ServiceInstance> instances = discoveryClient.getInstances("ms-calificacion");
        if (!instances.isEmpty()) {
            ServiceInstance instance = instances.get(0);
            return String.format("Service: %s, Host: %s, Port: %d",
                    instance.getServiceId(),
                    instance.getHost(),
                    instance.getPort()
            );
        }
        return "No instance found";
    }

    @Transactional(readOnly = true)
    public List<CalificacionResponse> obtenerTodas() {
        log.info("Obteniendo todas las calificaciones - Info: {}", getServiceInfo());
        return calificacionMapper.toResponseList(calificacionRepository.findAll());
    }

    @Transactional(readOnly = true)
    public CalificacionResponse obtenerPorId(Long id) {
        log.info("Buscando calificación con ID: {} - Info: {}", id, getServiceInfo());
        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new CalificacionNoEncontradaException("Calificación no encontrada con ID: " + id));
        return calificacionMapper.toResponse(calificacion);
    }

    @Transactional(readOnly = true)
    public List<CalificacionResponse> obtenerPorUsuario(Long idUsuario) {
        log.info("Buscando calificaciones del usuario: {} - Info: {}", idUsuario, getServiceInfo());
        return calificacionMapper.toResponseList(calificacionRepository.findByIdUsuario(idUsuario));
    }

    @Transactional(readOnly = true)
    public List<CalificacionResponse> obtenerPorReserva(Long idReserva) {
        log.info("Buscando calificaciones de la reserva: {} - Info: {}", idReserva, getServiceInfo());
        return calificacionMapper.toResponseList(calificacionRepository.findByIdReserva(idReserva));
    }

    @Transactional
    public CalificacionResponse crear(CalificacionRequest request) {
        log.info("Creando calificación para reserva: {} - Info: {}", request.getIdReserva(), getServiceInfo());

        // Validar que no exista calificación duplicada para la misma reserva y usuario
        if (calificacionRepository.existsByIdReservaAndIdUsuario(request.getIdReserva(), request.getIdUsuario())) {
            throw new IllegalArgumentException("Ya existe una calificación para esta reserva y usuario");
        }

        Calificacion calificacion = calificacionMapper.toEntity(request);
        Calificacion guardada = calificacionRepository.save(calificacion);
        log.info("Calificación creada correctamente con ID: {}", guardada.getId());

        return calificacionMapper.toResponse(guardada);
    }

    @Transactional
    public CalificacionResponse actualizar(Long id, CalificacionRequest request) {
        log.info("Actualizando calificación con ID: {} - Info: {}", id, getServiceInfo());

        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new CalificacionNoEncontradaException("Calificación no encontrada con ID: " + id));

        // Solo se puede actualizar si está en estado PENDIENTE
        if (calificacion.getEstado() != EstadoCalificacion.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden actualizar calificaciones en estado PENDIENTE");
        }

        calificacion.setPuntuacion(request.getPuntuacion());
        calificacion.setComentario(request.getComentario());

        Calificacion actualizada = calificacionRepository.save(calificacion);
        log.info("Calificación actualizada correctamente con ID: {}", id);

        return calificacionMapper.toResponse(actualizada);
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando calificación con ID: {} - Info: {}", id, getServiceInfo());

        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new CalificacionNoEncontradaException("Calificación no encontrada con ID: " + id));

        calificacionRepository.delete(calificacion);
        log.info("Calificación eliminada correctamente con ID: {}", id);
    }

    @Transactional
    public CalificacionResponse publicar(Long id) {
        log.info("Publicando calificación con ID: {} - Info: {}", id, getServiceInfo());

        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new CalificacionNoEncontradaException("Calificación no encontrada con ID: " + id));

        if (calificacion.getEstado() != EstadoCalificacion.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden publicar calificaciones en estado PENDIENTE");
        }

        calificacion.setEstado(EstadoCalificacion.PUBLICADA);
        Calificacion publicada = calificacionRepository.save(calificacion);
        log.info("Calificación publicada correctamente con ID: {}", id);

        return calificacionMapper.toResponse(publicada);
    }

    @Transactional
    public CalificacionResponse moderar(Long id, EstadoCalificacion nuevoEstado) {
        log.info("Moderando calificación con ID: {} a estado: {} - Info: {}", id, nuevoEstado, getServiceInfo());

        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new CalificacionNoEncontradaException("Calificación no encontrada con ID: " + id));

        calificacion.setEstado(nuevoEstado);
        Calificacion moderada = calificacionRepository.save(calificacion);
        log.info("Calificación moderada correctamente con ID: {}", id);

        return calificacionMapper.toResponse(moderada);
    }

    @Transactional(readOnly = true)
    public Double obtenerPromedioCalificaciones() {
        log.info("Calculando promedio de calificaciones - Info: {}", getServiceInfo());
        List<Calificacion> calificaciones = calificacionRepository.findByEstado(EstadoCalificacion.PUBLICADA);
        if (calificaciones.isEmpty()) {
            return 0.0;
        }
        double promedio = calificaciones.stream()
                .mapToInt(Calificacion::getPuntuacion)
                .average()
                .orElse(0.0);
        return Math.round(promedio * 10.0) / 10.0; // Redondear a 1 decimal
    }
}