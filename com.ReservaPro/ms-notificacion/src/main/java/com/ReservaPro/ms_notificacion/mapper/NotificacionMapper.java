package com.ReservaPro.ms_notificacion.mapper;

import com.ReservaPro.ms_notificacion.dto.request.NotificacionRequest;
import com.ReservaPro.ms_notificacion.dto.response.NotificacionResponse;
import com.ReservaPro.ms_notificacion.model.Notificacion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {

    Notificacion toEntity(NotificacionRequest notificacionRequest);
     //convierte dto auna entidas
    NotificacionResponse toResponse(Notificacion notificacion);
// covierte la entidad de la base datos en dto resp.
    List<NotificacionResponse> toResponseList(List<Notificacion> notificacionList);
}   //esta trae la lista  mapper solo comvierte los datos del dto