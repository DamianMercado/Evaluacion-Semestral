package com.ReservaPro.ms_reserva.service;

import com.ReservaPro.ms_reserva.dto.response.ReservaResponse;
import com.ReservaPro.ms_reserva.exception.ReservaNoEncontradoException;
import com.ReservaPro.ms_reserva.mapper.ReservaMapper;
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

    public List<ReservaResponse> obtenerPorId(Long id) {
        log.info("Obteniendo todas las reservas por id");
        return reservaMapper.toResponse(
                reservaRepository.findById(id).orElseThrow(() -> new ReservaNoEncontradoException(id)
                )
        );

    }


}
