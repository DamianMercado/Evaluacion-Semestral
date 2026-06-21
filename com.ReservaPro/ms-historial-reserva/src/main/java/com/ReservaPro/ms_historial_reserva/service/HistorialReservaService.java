package com.ReservaPro.ms_historial_reserva.service;

import com.ReservaPro.ms_historial_reserva.client.ReservaClient;
import com.ReservaPro.ms_historial_reserva.dto.request.HistorialReservaRequest;
import com.ReservaPro.ms_historial_reserva.dto.response.HistorialReservaResponse;
import com.ReservaPro.ms_historial_reserva.exception.HistorialReservaNoEncontradaException;
import com.ReservaPro.ms_historial_reserva.exception.ReglaNegocioException;
import com.ReservaPro.ms_historial_reserva.mapper.HistorialReservaMapper;
import com.ReservaPro.ms_historial_reserva.model.HistorialReserva;
import com.ReservaPro.ms_historial_reserva.repository.HistorialReservaRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistorialReservaService {

    private static final Logger log =
            LoggerFactory.getLogger(HistorialReservaService.class);

    private final HistorialReservaRepository historialReservaRepository;
    private final HistorialReservaMapper historialReservaMapper;
    private final ReservaClient reservaClient;

    public List<HistorialReservaResponse> obtener() {

        log.info("Obteniendo todos los historiales de reservas");

        return historialReservaMapper.toResponseList(
                historialReservaRepository.findAll()
        );
    }

    public HistorialReservaResponse obtenerPorId(Long id) {

        log.info("Buscando historial de reserva con ID: {}", id);

        return historialReservaMapper.toResponse(
                historialReservaRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new HistorialReservaNoEncontradaException(id)
                        )
        );
    }

    public HistorialReservaResponse crear(HistorialReservaRequest request) {

        log.info("Validando reserva ID: {}", request.getIdReserva());

        reservaClient.obtenerReservaPorId(request.getIdReserva());

        validarEstados(
                request.getEstadoAnterior(),
                request.getEstadoNuevo()
        );

        log.info(
                "Creando historial para reserva ID: {}",
                request.getIdReserva()
        );

        HistorialReserva historial =
                historialReservaMapper.toEntity(request);

        return historialReservaMapper.toResponse(
                historialReservaRepository.save(historial)
        );
    }

    public HistorialReservaResponse actualizar(
            Long id,
            HistorialReservaRequest request) {

        log.info("Actualizando historial de reserva ID: {}", id);

        HistorialReserva historialExistente =
                historialReservaRepository.findById(id)
                        .orElseThrow(() ->
                                new HistorialReservaNoEncontradaException(id)
                        );

        log.info("Validando reserva ID: {}", request.getIdReserva());

        reservaClient.obtenerReservaPorId(request.getIdReserva());

        validarEstados(
                request.getEstadoAnterior(),
                request.getEstadoNuevo()
        );

        historialExistente.setIdReserva(
                request.getIdReserva()
        );

        historialExistente.setEstadoAnterior(
                request.getEstadoAnterior()
        );

        historialExistente.setEstadoNuevo(
                request.getEstadoNuevo()
        );

        historialExistente.setObservacion(
                request.getObservacion()
        );

        return historialReservaMapper.toResponse(
                historialReservaRepository.save(historialExistente)
        );
    }

    public void eliminar(Long id) {

        log.info("Eliminando historial de reserva ID: {}", id);

        if (!historialReservaRepository.existsById(id)) {
            throw new HistorialReservaNoEncontradaException(id);
        }

        historialReservaRepository.deleteById(id);

        log.info(
                "Historial eliminado correctamente ID: {}",
                id
        );
    }

    public List<HistorialReservaResponse> obtenerPorReserva(
            Long idReserva) {

        log.info(
                "Obteniendo historial de la reserva ID: {}",
                idReserva
        );

        return historialReservaMapper.toResponseList(
                historialReservaRepository.findByIdReserva(idReserva)
        );
    }

    private void validarEstados(
            Object estadoAnterior,
            Object estadoNuevo) {

        if (estadoAnterior == estadoNuevo) {

            throw new ReglaNegocioException(
                    "El estado anterior y el nuevo no pueden ser iguales"
            );
        }
    }
}