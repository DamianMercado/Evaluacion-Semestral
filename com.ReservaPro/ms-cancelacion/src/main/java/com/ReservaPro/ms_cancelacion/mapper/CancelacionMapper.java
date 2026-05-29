package com.ReservaPro.ms_cancelacion.mapper;

import com.ReservaPro.ms_cancelacion.dto.request.CancelacionRequest;
import com.ReservaPro.ms_cancelacion.dto.response.CancelacionResponse;
import com.ReservaPro.ms_cancelacion.model.Cancelacion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CancelacionMapper {

    public Cancelacion toEntity(CancelacionRequest request) {

        Cancelacion cancelacion = new Cancelacion();

        cancelacion.setMotivo(request.getMotivo());
        cancelacion.setFechaCancelacion(request.getFechaCancelacion());
        cancelacion.setEstadoReembolso(request.getEstadoReembolso());

        return cancelacion;
    }

    public CancelacionResponse toResponse(Cancelacion cancelacion) {

        CancelacionResponse response = new CancelacionResponse();

        response.setIdCancelacion(cancelacion.getIdCancelacion());
        response.setMotivo(cancelacion.getMotivo());
        response.setFechaCancelacion(cancelacion.getFechaCancelacion());
        response.setEstadoReembolso(cancelacion.getEstadoReembolso());

        return response;
    }

    public List<CancelacionResponse> toResponseList(List<Cancelacion> lista) {

        return lista.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}