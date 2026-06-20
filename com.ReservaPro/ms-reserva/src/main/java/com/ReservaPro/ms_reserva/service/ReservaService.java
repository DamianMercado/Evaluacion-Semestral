package com.ReservaPro.ms_reserva.service;

import com.ReservaPro.ms_reserva.dto.request.ReservaRequest;
import com.ReservaPro.ms_reserva.dto.response.ReservaResponse;
import com.ReservaPro.ms_reserva.exception.ReservaNoEncontradoException;
import com.ReservaPro.ms_reserva.mapper.ReservaMapper;
import com.ReservaPro.ms_reserva.model.Reserva;
import com.ReservaPro.ms_reserva.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private static final Logger log =
            LoggerFactory.getLogger(ReservaService.class);

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    //Obtener todas las reservas

    public List<ReservaResponse> obtener() {

        log.info("Obteniendo todas las reservas");

        return reservaMapper.toResponseList(
                reservaRepository.findAll()
        );
    }

    //Obtener reserva por id

    public ReservaResponse obtenerPorId(Long id) {
        log.info("Obteniendo la reserva por id");

        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new ReservaNoEncontradoException(id));

        return reservaMapper.toResponse(reserva);

    }

    //Crear Reserva

    public ReservaResponse crear(ReservaRequest reserva) {
        Reserva reservaAux = reservaMapper.toEntity(reserva);
        log.info("Creando Reserva {}", reservaAux);

        return reservaMapper.toResponse(reservaRepository.save(reservaAux));
    }

    //Actualizar Reserva

    public ReservaResponse actualizar(
            Long id,
            ReservaRequest request) {

        log.info("Actualizando Reserva con el ID: {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() ->
                        new ReservaNoEncontradoException(id)
                );
//Ms Reserva

        reserva.setFechaReserva(
                request.getFechaReserva()
        );

        reserva.setEstadoReserva(
                request.getEstadoReserva()
        );

        reserva.setPrecioReserva(
                request.getPrecioReserva()
        );

        reserva.setPrecioFinal(
                request.getPrecioFinal()
        );

        reserva.setDescuentoAplicado(
                request.getDescuentoAplicado()
        );

//Otros ms

        reserva.setIdCalificacion(
                request.getIdCalificacion()
        );

        reserva.setIdUsuario(
                request.getIdUsuario()
        );

        reserva.setIdPago(
                request.getIdPago()
        );

        reserva.setIdPromocion(
                request.getIdPromocion()
        );

        reserva.setIdGestionServicio(
                request.getIdGestionServicio()
        );

        Reserva actualizada =
                reservaRepository.save(reserva);

        log.info(
                "Reserva actualizado correctamente con ID: {}",
                id
        );

        return reservaMapper.toResponse(actualizada);
    }

    //Eliminar Reserva

    public void eliminar(Long id) {

        log.info("Eliminando reserva por ID: {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() ->
                        new ReservaNoEncontradoException(id)
                );

        reservaRepository.delete(reserva);

        log.info(
                "Reserva eliminada correctamente ID: {}",
                id
        );
    }


}
