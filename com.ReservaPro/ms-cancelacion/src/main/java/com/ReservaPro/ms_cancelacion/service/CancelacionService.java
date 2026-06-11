package com.ReservaPro.ms_cancelacion.service;

import com.ReservaPro.ms_cancelacion.dto.request.CancelacionRequest;
import com.ReservaPro.ms_cancelacion.dto.response.CancelacionResponse;
import com.ReservaPro.ms_cancelacion.exception.CancelacionNotFoundException;
import com.ReservaPro.ms_cancelacion.mapper.CancelacionMapper;
import com.ReservaPro.ms_cancelacion.model.Cancelacion;
import com.ReservaPro.ms_cancelacion.repository.CancelacionRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CancelacionService {

    private static final Logger log =
            LoggerFactory.getLogger(CancelacionService.class);

    private final CancelacionRepository cancelacionRepository;
    private final CancelacionMapper cancelacionMapper;

    public List<CancelacionResponse> obtener() {

        log.info("Obteniendo todas las cancelaciones");

        return cancelacionMapper.toResponseList(
                cancelacionRepository.findAll()
        );
    }

    public CancelacionResponse obtenerPorId(Long id) {

        log.info("Buscando cancelación con ID: {}", id);

        return cancelacionMapper.toResponse(
                cancelacionRepository.findById(id)
                        .orElseThrow(() ->
                                new CancelacionNotFoundException(id))
        );
    }

    public CancelacionResponse crear(
            CancelacionRequest cancelacionRequest) {

        log.info(
                "Creando cancelación. Motivo: {}",
                cancelacionRequest.getMotivo()
        );

        Cancelacion cancelacion = cancelacionRepository.save(
                cancelacionMapper.toEntity(cancelacionRequest)
        );

        log.info(
                "Cancelación creada correctamente con ID: {}",
                cancelacion.getIdCancelacion()
        );

        return cancelacionMapper.toResponse(cancelacion);
    }

    public void eliminar(Long id) {

        log.info("Eliminando cancelación con ID: {}", id);

        if (!cancelacionRepository.existsById(id)) {

            log.error("No existe la cancelación con ID: {}", id);

            throw new CancelacionNotFoundException(id);
        }

        cancelacionRepository.deleteById(id);

        log.info("Cancelación eliminada correctamente con ID: {}", id);
    }

    public CancelacionResponse actualizar(
            Long id,
            CancelacionRequest cancelacionRequest) {

        log.info("Actualizando cancelación con ID: {}", id);

        Cancelacion cancelacionExistente =
                cancelacionRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error(
                                    "No se encontró la cancelación con ID: {}",
                                    id
                            );
                            return new CancelacionNotFoundException(id);
                        });

        try {

            cancelacionExistente.setMotivo(
                    cancelacionRequest.getMotivo());

            cancelacionExistente.setFechaCancelacion(
                    cancelacionRequest.getFechaCancelacion());

            cancelacionExistente.setEstadoReembolso(
                    cancelacionRequest.getEstadoReembolso());

        } catch (Exception e) {

            log.error(
                    "Error actualizando cancelación ID {}: {}",
                    id,
                    e.getMessage()
            );

            throw new RuntimeException(e);
        }

        Cancelacion actualizada = cancelacionRepository.save(
                cancelacionExistente
        );

        log.info(
                "Cancelación actualizada correctamente con ID: {}",
                id
        );

        return cancelacionMapper.toResponse(actualizada);
    }
}