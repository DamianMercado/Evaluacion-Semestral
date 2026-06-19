package com.ReservaPro.ms_pago.mapper;

import com.ReservaPro.ms_pago.dto.request.PagoRequest;
import com.ReservaPro.ms_pago.dto.response.PagoResponse;
import com.ReservaPro.ms_pago.model.Pago;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PagoMapper {
    Pago toEntity(PagoRequest pagoRequest);
    PagoResponse toResponse(Pago pago);
}
