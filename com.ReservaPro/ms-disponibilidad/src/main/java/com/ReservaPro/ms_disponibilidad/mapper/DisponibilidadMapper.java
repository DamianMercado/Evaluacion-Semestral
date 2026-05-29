package com.ReservaPro.ms_disponibilidad.mapper;

import com.ReservaPro.ms_disponibilidad.dto.request.DisponibilidadRequest;
import com.ReservaPro.ms_disponibilidad.dto.response.DisponibilidadResponse;
import com.ReservaPro.ms_disponibilidad.model.Disponibilidad;

public class DisponibilidadMapper {

    public static Disponibilidad toEntity(DisponibilidadRequest request) {
        return Disponibilidad.builder()
                .fecha(request.getFecha())
                .horaInicio(request.getHoraInicio())
                .horaFin(request.getHoraFin())
                .cuposDisponibles(request.getCuposDisponibles())
                .cuposTotales(request.getCuposTotales())
                .estado(request.getEstado())
                .observacion(request.getObservacion())
                .activo(request.getActivo())
                .build();
    }

    public static DisponibilidadResponse toResponse(Disponibilidad disponibilidad) {
        return DisponibilidadResponse.builder()
                .idDisponibilidad(disponibilidad.getIdDisponibilidad())
                .fecha(disponibilidad.getFecha())
                .horaInicio(disponibilidad.getHoraInicio())
                .horaFin(disponibilidad.getHoraFin())
                .cuposDisponibles(disponibilidad.getCuposDisponibles())
                .cuposTotales(disponibilidad.getCuposTotales())
                .estado(disponibilidad.getEstado())
                .observacion(disponibilidad.getObservacion())
                .activo(disponibilidad.getActivo())
                .fechaCreacion(disponibilidad.getFechaCreacion())
                .build();
    }
}