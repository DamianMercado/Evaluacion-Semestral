package com.ReservaPro.ms_reserva.mapper;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    Reserva toEntity(ReservaRequest reservaRequest);

    ReservaResponse toResponse(Reserva reserva);

    List<ReservaResponse> toResponseList(List<Reserva> notificacionList);
}
