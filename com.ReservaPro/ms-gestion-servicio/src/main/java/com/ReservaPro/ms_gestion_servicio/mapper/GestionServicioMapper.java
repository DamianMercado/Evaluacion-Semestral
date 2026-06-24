package com.ReservaPro.ms_gestion_servicio.mapper;

import com.ReservaPro.ms_gestion_servicio.dto.request.GestionServicioRequest;
import com.ReservaPro.ms_gestion_servicio.dto.response.GestionServicioResponse;
import com.ReservaPro.ms_gestion_servicio.enums.EstadoServicio;
import com.ReservaPro.ms_gestion_servicio.model.GestionServicio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GestionServicioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estadoServicio", source = "estadoServicio", qualifiedByName = "stringToEstadoServicio")
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    GestionServicio toEntity(GestionServicioRequest request);

    @Mapping(target = "estadoServicio", source = "estadoServicio", qualifiedByName = "estadoServicioToString")
    GestionServicioResponse toResponse(GestionServicio gestionServicio);

    List<GestionServicioResponse> toResponseList(List<GestionServicio> gestionServicioList);

    @Named("stringToEstadoServicio")
    default EstadoServicio stringToEstadoServicio(String estado) {
        if (estado == null || estado.isEmpty()) {
            return EstadoServicio.ACTIVADO;
        }
        return EstadoServicio.fromValor(estado);
    }

    @Named("estadoServicioToString")
    default String estadoServicioToString(EstadoServicio estado) {
        if (estado == null) {
            return EstadoServicio.ACTIVADO.getValor();
        }
        return estado.getValor();
    }
}