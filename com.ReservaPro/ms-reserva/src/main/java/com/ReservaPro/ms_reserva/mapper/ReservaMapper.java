package com.ReservaPro.ms_reserva.mapper;

// Importa el DTO que recibe datos desde el cliente
import com.ReservaPro.ms_reserva.dto.request.ReservaRequest;

// Importa el DTO que devuelve información al cliente
import com.ReservaPro.ms_reserva.dto.response.ReservaResponse;

// Importa el enum EstadoReserva
import com.ReservaPro.ms_reserva.enums.EstadoReserva;

// Importa la entidad Reserva
import com.ReservaPro.ms_reserva.model.Reserva;

// Importa anotaciones de MapStruct
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

// Indica que esta interfaz será implementada automáticamente por MapStruct
// componentModel = "spring" permite que Spring la inyecte automáticamente
@Mapper(componentModel = "spring")
public interface ReservaMapper {

    // Convierte un ReservaRequest en una entidad Reserva
    @Mapping(target = "id", ignore = true)
    // El estado se asigna desde el Service
    @Mapping(target = "estadoReserva", ignore = true)

    // El precioReserva se asigna desde el Service, consultando
    // el precioServicio real en ms-gestion-servicio
    @Mapping(target = "precioReserva", ignore = true)

    // La fecha de creación se genera automáticamente
    @Mapping(target = "fechaCreacion", ignore = true)

    // La fecha de actualización se genera automáticamente
    @Mapping(target = "fechaActualizacion", ignore = true)
    Reserva toEntity(ReservaRequest request);

    // Convierte una entidad Reserva en un ReservaResponse
    @Mapping(
            target = "estadoReserva",
            source = "estadoReserva",
            qualifiedByName = "estadoReservaToString"
    )
    ReservaResponse toResponse(Reserva reserva);

    // Convierte una lista de entidades Reserva
    // en una lista de ReservaResponse
    List<ReservaResponse> toResponseList(
            List<Reserva> reservas
    );

    // Métodos personalizado para convertir el enum EstadoReserva a String
    @Named("estadoReservaToString")
    default String estadoReservaToString(
            EstadoReserva estado) {

        // Si el estado existe devuelve su valor
        // Si no existe devuelve null
        return estado != null
                ? estado.getValor()
                : null;
    }
}