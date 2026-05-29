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

    private final CancelacionRepository cancelacionRepository;
    private final CancelacionMapper cancelacionMapper;

    private static final Logger log =
            LoggerFactory.getLogger(CancelacionService.class);

    public List<CancelacionResponse> obtener() {

        return cancelacionMapper.toResponseList(
                cancelacionRepository.findAll()
        );
    }

    public CancelacionResponse obtenerPorId(Long id) {

        log.info("Se está obteniendo una cancelacion con id {}", id);

        return cancelacionMapper.toResponse(
                cancelacionRepository.findById(id)
                        .orElseThrow(() ->
                                new CancelacionNotFoundException(id))
        );
    }

    public CancelacionResponse crear(
            CancelacionRequest cancelacionRequest) {

        return cancelacionMapper.toResponse(
                cancelacionRepository.save(
                        cancelacionMapper.toEntity(cancelacionRequest)
                )
        );
    }

    public void eliminar(Long id) {

        if (!cancelacionRepository.existsById(id)) {
            throw new CancelacionNotFoundException(id);
        }

        cancelacionRepository.deleteById(id);
    }

    public CancelacionResponse actualizar(
            Long id,
            CancelacionRequest cancelacionRequest) {

        Cancelacion cancelacionExistente =
                cancelacionRepository.findById(id)
                        .orElseThrow(() ->
                                new CancelacionNotFoundException(id));

        try {

            cancelacionExistente.setMotivo(
                    cancelacionRequest.getMotivo());

            cancelacionExistente.setFechaCancelacion(
                    cancelacionRequest.getFechaCancelacion());

            cancelacionExistente.setEstadoReembolso(
                    cancelacionRequest.getEstadoReembolso());

        } catch (Exception e) {

            log.error(
                    "Error actualizando cancelacion: {}",
                    cancelacionRequest
            );

            throw new RuntimeException(e);
        }

        return cancelacionMapper.toResponse(
                cancelacionRepository.save(cancelacionExistente)
        );
    }
}