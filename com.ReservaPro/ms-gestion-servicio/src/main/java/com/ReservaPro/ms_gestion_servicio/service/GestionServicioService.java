package com.ReservaPro.ms_gestion_servicio.service;

import com.ReservaPro.ms_gestion_servicio.client.UsuarioOperadorClient;
import com.ReservaPro.ms_gestion_servicio.dto.request.GestionServicioRequest;
import com.ReservaPro.ms_gestion_servicio.dto.response.GestionServicioResponse;
import com.ReservaPro.ms_gestion_servicio.enums.EstadoServicio;
import com.ReservaPro.ms_gestion_servicio.exception.GestionServicioNotFoundException;
import com.ReservaPro.ms_gestion_servicio.mapper.GestionServicioMapper;
import com.ReservaPro.ms_gestion_servicio.model.GestionServicio;
import com.ReservaPro.ms_gestion_servicio.repository.GestionServicioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GestionServicioService {

    private static final Logger log = LoggerFactory.getLogger(GestionServicioService.class);

    private final GestionServicioRepository gestionServicioRepository;
    private final GestionServicioMapper gestionServicioMapper;

    @Transactional(readOnly = true)
    public List<GestionServicioResponse> obtenerTodos() {
        log.info("Obteniendo todos los servicios");
        return gestionServicioMapper.toResponseList(gestionServicioRepository.findAll());
    }

    @Transactional(readOnly = true)
    public GestionServicioResponse obtenerPorId(Long id) {
        log.info("Buscando servicio con ID: {}", id);
        GestionServicio gestionServicio = gestionServicioRepository.findById(id)
                .orElseThrow(() -> new GestionServicioNotFoundException(id));
        return gestionServicioMapper.toResponse(gestionServicio);
    }

    @Transactional(readOnly = true)
    public List<GestionServicioResponse> obtenerPorEstado(EstadoServicio estado) {
        log.info("Buscando servicios por estado: {}", estado.getValor());
        return gestionServicioMapper.toResponseList(
                gestionServicioRepository.findByEstadoServicio(estado)
        );
    }

    @Transactional(readOnly = true)
    public List<GestionServicioResponse> buscarPorNombre(String nombre) {
        log.info("Buscando servicios por nombre: {}", nombre);
        return gestionServicioMapper.toResponseList(
                gestionServicioRepository.findByNombreContainingIgnoreCase(nombre)
        );
    }

    @Transactional(readOnly = true)
    public List<GestionServicioResponse> obtenerPorProveedor(Long proveedorId) {
        log.info("Buscando servicios por proveedor: {}", proveedorId);
        return gestionServicioMapper.toResponseList(
                gestionServicioRepository.findByProveedorId(proveedorId)
        );
    }

    @Transactional
    public GestionServicioResponse crear(GestionServicioRequest request) {
        log.info("Creando nuevo servicio: {}", request.getNombre());

        // Validar que no exista un servicio con el mismo nombre
        if (gestionServicioRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new IllegalArgumentException("Ya existe un servicio con el nombre: " + request.getNombre());
        }

        GestionServicio gestionServicio = gestionServicioMapper.toEntity(request);

        // Si no se especifica estado, por defecto ACTIVADO
        if (gestionServicio.getEstadoServicio() == null) {
            gestionServicio.setEstadoServicio(EstadoServicio.ACTIVADO);
        }

        GestionServicio guardado = gestionServicioRepository.save(gestionServicio);
        log.info("Servicio creado correctamente con ID: {}", guardado.getId());

        return gestionServicioMapper.toResponse(guardado);
    }

    @Transactional
    public GestionServicioResponse actualizar(Long id, GestionServicioRequest request) {
        log.info("Actualizando servicio con ID: {}", id);

        GestionServicio gestionServicio = gestionServicioRepository.findById(id)
                .orElseThrow(() -> new GestionServicioNotFoundException(id));

        // Validar que no exista otro servicio con el mismo nombre
        if (!gestionServicio.getNombre().equalsIgnoreCase(request.getNombre()) &&
                gestionServicioRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new IllegalArgumentException("Ya existe un servicio con el nombre: " + request.getNombre());
        }

        gestionServicio.setNombre(request.getNombre());
        gestionServicio.setPrecioServicio(request.getPrecioServicio());
        gestionServicio.setDuracionMinuto(request.getDuracionMinuto());
        gestionServicio.setEstadoServicio(gestionServicioMapper.stringToEstadoServicio(request.getEstadoServicio()));
        gestionServicio.setUbicacion(request.getUbicacion());
        gestionServicio.setCapacidad(request.getCapacidad());
        gestionServicio.setCondiciones(request.getCondiciones());
        gestionServicio.setProveedorId(request.getProveedorId());

        GestionServicio actualizado = gestionServicioRepository.save(gestionServicio);
        log.info("Servicio actualizado correctamente con ID: {}", id);

        return gestionServicioMapper.toResponse(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando servicio con ID: {}", id);

        GestionServicio gestionServicio = gestionServicioRepository.findById(id)
                .orElseThrow(() -> new GestionServicioNotFoundException(id));

        // Eliminación lógica: cambiar estado a ELIMINADO
        gestionServicio.setEstadoServicio(EstadoServicio.ELIMINADO);
        gestionServicioRepository.save(gestionServicio);

        log.info("Servicio eliminado lógicamente con ID: {}", id);
    }

    @Transactional
    public GestionServicioResponse cambiarEstado(Long id, EstadoServicio nuevoEstado) {
        log.info("Cambiando estado del servicio ID: {} a {}", id, nuevoEstado.getValor());

        GestionServicio gestionServicio = gestionServicioRepository.findById(id)
                .orElseThrow(() -> new GestionServicioNotFoundException(id));

        gestionServicio.setEstadoServicio(nuevoEstado);
        GestionServicio actualizado = gestionServicioRepository.save(gestionServicio);

        log.info("Estado cambiado correctamente para el servicio ID: {}", id);
        return gestionServicioMapper.toResponse(actualizado);
    }
}