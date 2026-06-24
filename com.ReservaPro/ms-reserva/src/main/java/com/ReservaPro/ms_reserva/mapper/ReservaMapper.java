package com.ReservaPro.ms_reserva.mapper;

import com.ReservaPro.ms_reserva.dto.request.ReservaRequest;
import com.ReservaPro.ms_reserva.dto.response.ReservaResponse;
import com.ReservaPro.ms_reserva.enums.EstadoReserva;
import com.ReservaPro.ms_reserva.model.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estadoReserva", ignore = true) // Se asigna en el Service
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    Reserva toEntity(ReservaRequest request);

    @Mapping(target = "estadoReserva", source = "estadoReserva", qualifiedByName = "estadoReservaToString")
    ReservaResponse toResponse(Reserva reserva);

    List<ReservaResponse> toResponseList(List<Reserva> reservas);

    @Named("estadoReservaToString")
    default String estadoReservaToString(EstadoReserva estado) {
        return estado != null ? estado.getValor() : null;
    }
}