package com.ReservaPro.ms_cancelacion.mapper;
// Define el paquete donde se encuentra el Mapper

import com.ReservaPro.ms_cancelacion.dto.request.CancelacionRequest;
// Importa el DTO Request que recibe datos desde Swagger o Postman

import com.ReservaPro.ms_cancelacion.dto.response.CancelacionResponse;
// Importa el DTO Response que devuelve información al cliente

import com.ReservaPro.ms_cancelacion.model.Cancelacion;
// Importa la entidad Cancelacion

import org.mapstruct.Mapper;
// Importa la anotación Mapper de MapStruct

import java.util.List;
// Permite trabajar con listas

@Mapper(componentModel = "spring")
// Indica que esta interfaz será implementada automáticamente por MapStruct
// componentModel="spring" permite que Spring gestione el Mapper como un Bean

public interface CancelacionMapper {
    // Interfaz encargada de convertir objetos

    Cancelacion toEntity(
            CancelacionRequest cancelacionRequest
    );
    // Convierte un objeto CancelacionRequest en una entidad Cancelacion
    // Se utiliza principalmente en los métodos crear() y actualizar()

    CancelacionResponse toResponse(
            Cancelacion cancelacion
    );
    // Convierte una entidad Cancelacion en un DTO CancelacionResponse
    // Se utiliza para devolver datos al cliente

    List<CancelacionResponse> toResponseList(
            List<Cancelacion> cancelacionList
    );
    // Convierte una lista de entidades Cancelacion en una lista de DTO Response
    // Se utiliza cuando se obtienen varias cancelaciones desde la BD
}