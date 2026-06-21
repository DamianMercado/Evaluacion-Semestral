package com.ReservaPro.ms_disponibilidad.mapper;

import com.ReservaPro.ms_disponibilidad.dto.request.DisponibilidadRequest;
import com.ReservaPro.ms_disponibilidad.dto.response.DisponibilidadResponse;
import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DisponibilidadMapper {

    Disponibilidad toEntity(
            DisponibilidadRequest disponibilidadRequest);

    DisponibilidadResponse toResponse(
            Disponibilidad disponibilidad);

    List<DisponibilidadResponse> toResponseList(
            List<Disponibilidad> disponibilidadList);
}