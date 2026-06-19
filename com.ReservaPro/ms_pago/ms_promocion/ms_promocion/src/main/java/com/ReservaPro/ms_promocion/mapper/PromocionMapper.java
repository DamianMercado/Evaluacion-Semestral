package com.ReservaPro.ms_promocion.mapper;

import com.ReservaPro.ms_promocion.dto.request.PromocionRequest;
import com.ReservaPro.ms_promocion.dto.response.PromocionResponse;
import com.ReservaPro.ms_promocion.model.Promocion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface PromocionMapper {

    @Mapping(target = "codigoPromocion", expression = "java(promocionRequest.getCodigoPromocion() != null ? promocionRequest.getCodigoPromocion().toUpperCase() : null)")
    Promocion toEntity(PromocionRequest promocionRequest);

    PromocionResponse toResponse(Promocion promocion);
}
