package com.ReservaPro.ms_pago.mapper;

import com.ReservaPro.ms_pago.dto.request.PagoRequest;
import com.ReservaPro.ms_pago.dto.response.PagoResponse;
import com.ReservaPro.ms_pago.enums.Estado;
import com.ReservaPro.ms_pago.model.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    @Mapping(target = "idPago", ignore = true)
    @Mapping(target = "estadoPago", constant = "PENDIENTE")
    @Mapping(target = "fechaPago", ignore = true)
    @Mapping(target = "aplicaDescuento", constant = "false")
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    Pago toEntity(PagoRequest request);

    @Mapping(target = "descuento", ignore = true)
    PagoResponse toResponse(Pago pago);

    @Named("estadoToString")
    default String estadoToString(Estado estado) {
        return estado != null ? estado.getValor() : null;
    }
}