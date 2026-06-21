package com.ReservaPro.ms_cancelacion.service;

import com.ReservaPro.ms_cancelacion.client.ReservaClient;
import com.ReservaPro.ms_cancelacion.dto.request.CancelacionRequest;
import com.ReservaPro.ms_cancelacion.dto.response.CancelacionResponse;
import com.ReservaPro.ms_cancelacion.exception.CancelacionNotFoundException;
import com.ReservaPro.ms_cancelacion.exception.ReglaNegocioException;
import com.ReservaPro.ms_cancelacion.mapper.CancelacionMapper;
import com.ReservaPro.ms_cancelacion.model.Cancelacion;
import com.ReservaPro.ms_cancelacion.repository.CancelacionRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CancelacionService {

    private static final Logger log =
            LoggerFactory.getLogger(CancelacionService.class);

    private final CancelacionRepository cancelacionRepository;
    private final CancelacionMapper cancelacionMapper;
    private final ReservaClient reservaClient;

    public List<CancelacionResponse> obtener() {

        log.info("Obteniendo todas las cancelaciones");

        return cancelacionMapper.toResponseList(
                cancelacionRepository.findAll()
        );
    }

    public CancelacionResponse obtenerPorId(Long id) {

        log.info("Buscando cancelación con ID: {}", id);

        Cancelacion cancelacion = cancelacionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No se encontró la cancelación con ID: {}", id);
                    return new CancelacionNotFoundException(id);
                });

        return cancelacionMapper.toResponse(cancelacion);
    }

    public CancelacionResponse crear(
            CancelacionRequest cancelacionRequest) {

        log.info(
                "Creando cancelación. Motivo: {}",
                cancelacionRequest.getMotivo()
        );

        validarCancelacion(cancelacionRequest);

        reservaClient.obtenerReservaPorId(
                cancelacionRequest.getIdReserva()
        );

        Cancelacion cancelacion =
                cancelacionMapper.toEntity(cancelacionRequest);

        Cancelacion guardada =
                cancelacionRepository.save(cancelacion);

        log.info(
                "Cancelación creada correctamente con ID: {}",
                guardada.getIdCancelacion()
        );

        return cancelacionMapper.toResponse(guardada);
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

        validarCancelacion(cancelacionRequest);

        reservaClient.obtenerReservaPorId(
                cancelacionRequest.getIdReserva()
        );

        cancelacionExistente.setIdReserva(
                cancelacionRequest.getIdReserva()
        );

        cancelacionExistente.setMotivo(
                cancelacionRequest.getMotivo()
        );

        cancelacionExistente.setFechaCancelacion(
                cancelacionRequest.getFechaCancelacion()
        );

        cancelacionExistente.setEstadoReembolso(
                cancelacionRequest.getEstadoReembolso()
        );

        Cancelacion actualizada =
                cancelacionRepository.save(cancelacionExistente);

        log.info(
                "Cancelación actualizada correctamente con ID: {}",
                id
        );

        return cancelacionMapper.toResponse(actualizada);
    }

    public void eliminar(Long id) {

        log.info("Eliminando cancelación con ID: {}", id);

        Cancelacion cancelacion =
                cancelacionRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error(
                                    "No se encontró la cancelación con ID: {}",
                                    id
                            );
                            return new CancelacionNotFoundException(id);
                        });

        cancelacionRepository.delete(cancelacion);

        log.info(
                "Cancelación eliminada correctamente con ID: {}",
                id
        );
    }

    private void validarCancelacion(
            CancelacionRequest request) {

        if (request.getMotivo() == null
                || request.getMotivo().isBlank()) {

            throw new ReglaNegocioException(
                    "El motivo de la cancelación es obligatorio"
            );
        }

        if (request.getFechaCancelacion()
                .isAfter(LocalDate.now())) {

            throw new ReglaNegocioException(
                    "La fecha de cancelación no puede ser futura"
            );
        }
    }
}