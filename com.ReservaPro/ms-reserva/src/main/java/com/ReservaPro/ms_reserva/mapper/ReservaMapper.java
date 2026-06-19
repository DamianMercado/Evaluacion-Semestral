package com.ReservaPro.ms_reserva.mapper;

import com.ReservaPro.ms_reserva.dto.request.ReservaRequest;
import com.ReservaPro.ms_reserva.dto.response.ReservaResponse;
import com.ReservaPro.ms_reserva.model.Reserva;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    Reserva toEntity(ReservaRequest reservaRequest);

    ReservaResponse toResponse(Reserva reserva);

    List<ReservaResponse> toResponseList(List<Reserva> reservaList);
}
