package com.ReservaPro.ms_historial_reserva.mapper;

import com.ReservaPro.ms_historial_reserva.dto.request.HistorialReservaRequest;
import com.ReservaPro.ms_historial_reserva.dto.response.HistorialReservaResponse;
import com.ReservaPro.ms_historial_reserva.model.HistorialReserva;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistorialReservaMapper {

    HistorialReserva toEntity(
            HistorialReservaRequest historialReservaRequest
    );

    HistorialReservaResponse toResponse(
            HistorialReserva historialReserva
    );

    List<HistorialReservaResponse> toResponseList(
            List<HistorialReserva> historialReservaList
    );
}