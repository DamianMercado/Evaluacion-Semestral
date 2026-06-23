package com.ReservaPro.ms_calificacion.mapper;

import com.ReservaPro.ms_calificacion.dto.request.CalificacionRequest;
import com.ReservaPro.ms_calificacion.dto.response.CalificacionResponse;
import com.ReservaPro.ms_calificacion.model.Calificacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CalificacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", constant = "PENDIENTE")
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    Calificacion toEntity(CalificacionRequest request);

    CalificacionResponse toResponse(Calificacion calificacion);

    List<CalificacionResponse> toResponseList(List<Calificacion> calificaciones);
}