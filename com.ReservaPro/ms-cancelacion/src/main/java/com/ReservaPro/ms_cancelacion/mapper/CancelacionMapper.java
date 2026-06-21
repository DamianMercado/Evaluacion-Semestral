package com.ReservaPro.ms_cancelacion.mapper;

import com.ReservaPro.ms_cancelacion.dto.request.CancelacionRequest;
import com.ReservaPro.ms_cancelacion.dto.response.CancelacionResponse;
import com.ReservaPro.ms_cancelacion.model.Cancelacion;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CancelacionMapper {

    Cancelacion toEntity(
            CancelacionRequest cancelacionRequest
    );

    CancelacionResponse toResponse(
            Cancelacion cancelacion
    );

    List<CancelacionResponse> toResponseList(
            List<Cancelacion> cancelacionList
    );
}