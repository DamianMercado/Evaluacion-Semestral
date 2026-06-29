package com.ReservaPro.ms_historial_reserva.mapper; // Define el paquete donde se encuentra el Mapper

import com.ReservaPro.ms_historial_reserva.dto.request.HistorialReservaRequest; // Importa el DTO Request
import com.ReservaPro.ms_historial_reserva.dto.response.HistorialReservaResponse; // Importa el DTO Response
import com.ReservaPro.ms_historial_reserva.model.HistorialReserva; // Importa la entidad HistorialReserva

import org.mapstruct.Mapper; // Importa la anotación Mapper de MapStruct

import java.util.List; // Importa la interfaz List

@Mapper(componentModel = "spring") // Indica que MapStruct generará automáticamente la implementación y Spring la administrará
public interface HistorialReservaMapper { // Define la interfaz Mapper

    HistorialReserva toEntity( // Convierte un Request en una entidad
                               HistorialReservaRequest historialReservaRequest // Recibe un DTO Request
    );

    HistorialReservaResponse toResponse( // Convierte una entidad en un DTO Response
                                         HistorialReserva historialReserva // Recibe la entidad HistorialReserva
    );

    List<HistorialReservaResponse> toResponseList( // Convierte una lista de entidades en una lista de DTO Response
                                                   List<HistorialReserva> historialReservaList // Recibe una lista de entidades
    );
}