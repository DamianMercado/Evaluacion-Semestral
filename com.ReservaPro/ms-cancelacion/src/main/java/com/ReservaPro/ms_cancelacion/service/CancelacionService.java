package com.ReservaPro.ms_cancelacion.service; // Paquete donde está el Service

import com.ReservaPro.ms_cancelacion.client.ReservaClient; // Cliente Feign para comunicarse con ms-reserva
import com.ReservaPro.ms_cancelacion.dto.request.CancelacionRequest; // DTO que recibe datos desde Swagger/Postman
import com.ReservaPro.ms_cancelacion.dto.response.CancelacionResponse; // DTO que devuelve datos al cliente
import com.ReservaPro.ms_cancelacion.exception.CancelacionNotFoundException; // Excepción cuando no existe cancelación
import com.ReservaPro.ms_cancelacion.exception.ReglaNegocioException; // Excepción para reglas de negocio
import com.ReservaPro.ms_cancelacion.mapper.CancelacionMapper; // Mapper para convertir DTO y entidad
import com.ReservaPro.ms_cancelacion.model.Cancelacion; // Entidad Cancelacion
import com.ReservaPro.ms_cancelacion.model.EstadoReembolso; // Enum del estado del reembolso
import com.ReservaPro.ms_cancelacion.repository.CancelacionRepository; // Repository para acceder a la base de datos

import lombok.RequiredArgsConstructor; // Crea constructor automático con atributos final

import org.slf4j.Logger; // Permite usar logs
import org.slf4j.LoggerFactory; // Permite crear el logger

import org.springframework.stereotype.Service; // Marca la clase como Service

import java.time.LocalDate; // Permite usar fechas
import java.util.List; // Permite usar listas

@Service // Indica que esta clase pertenece a la capa Service
@RequiredArgsConstructor // Inyecta automáticamente Repository, Mapper y Client
public class CancelacionService { // Clase con la lógica de negocio

    private static final Logger log =
            LoggerFactory.getLogger(CancelacionService.class); // Crea logger para mostrar mensajes en consola

    private final CancelacionRepository cancelacionRepository; // Permite consultar, guardar y eliminar en BD
    private final CancelacionMapper cancelacionMapper; // Convierte Entity a Response y Request a Entity
    private final ReservaClient reservaClient; // Permite validar una reserva llamando a ms-reserva

    public List<CancelacionResponse> obtener() { // Método para obtener todas las cancelaciones

        log.info("Obteniendo todas las cancelaciones"); // Muestra mensaje en consola

        return cancelacionMapper.toResponseList( // Convierte lista de entidades a lista de Response
                cancelacionRepository.findAll() // Busca todas las cancelaciones en BD
        );
    }

    public CancelacionResponse obtenerPorId(Long id) { // Método para buscar una cancelación por ID

        log.info("Buscando cancelación con ID: {}", id); // Muestra el ID buscado

        Cancelacion cancelacion = cancelacionRepository.findById(id) // Busca en BD por ID
                .orElseThrow(() -> { // Si no existe, lanza excepción
                    log.error("No se encontró la cancelación con ID: {}", id); // Log de error
                    return new CancelacionNotFoundException(id); // Excepción personalizada
                });

        return cancelacionMapper.toResponse(cancelacion); // Convierte entidad encontrada a Response
    }

    public CancelacionResponse crear(
            CancelacionRequest cancelacionRequest) { // Método para crear una cancelación

        log.info(
                "Creando cancelación. Motivo: {}",
                cancelacionRequest.getMotivo()
        ); // Muestra el motivo en consola

        validarCancelacion(cancelacionRequest); // Valida reglas antes de guardar

        reservaClient.obtenerReservaPorId(
                cancelacionRequest.getIdReserva()
        ); // Valida que la reserva exista en ms-reserva

        Cancelacion cancelacion =
                cancelacionMapper.toEntity(cancelacionRequest); // Convierte Request a Entity

        Cancelacion guardada =
                cancelacionRepository.save(cancelacion); // Guarda cancelación en BD

        log.info(
                "Cancelación creada correctamente con ID: {}",
                guardada.getIdCancelacion()
        ); // Muestra ID creado

        return cancelacionMapper.toResponse(guardada); // Devuelve Response al cliente
    }

    public CancelacionResponse actualizar(
            Long id,
            CancelacionRequest cancelacionRequest) { // Método para actualizar una cancelación

        log.info("Actualizando cancelación con ID: {}", id); // Log de actualización

        Cancelacion cancelacionExistente =
                cancelacionRepository.findById(id) // Busca la cancelación existente
                        .orElseThrow(() -> { // Si no existe, lanza excepción
                            log.error(
                                    "No se encontró la cancelación con ID: {}",
                                    id
                            ); // Log de error
                            return new CancelacionNotFoundException(id); // Excepción personalizada
                        });

        validarCancelacion(cancelacionRequest); // Valida los nuevos datos

        reservaClient.obtenerReservaPorId(
                cancelacionRequest.getIdReserva()
        ); // Valida que la reserva exista

        cancelacionExistente.setIdReserva(
                cancelacionRequest.getIdReserva()
        ); // Actualiza ID de reserva

        cancelacionExistente.setMotivo(
                cancelacionRequest.getMotivo()
        ); // Actualiza motivo

        cancelacionExistente.setFechaCancelacion(
                cancelacionRequest.getFechaCancelacion()
        ); // Actualiza fecha de cancelación

        cancelacionExistente.setEstadoReembolso(
                cancelacionRequest.getEstadoReembolso()
        ); // Actualiza estado del reembolso

        Cancelacion actualizada =
                cancelacionRepository.save(cancelacionExistente); // Guarda cambios en BD

        log.info(
                "Cancelación actualizada correctamente con ID: {}",
                id
        ); // Log de actualización correcta

        return cancelacionMapper.toResponse(actualizada); // Devuelve Response actualizado
    }

    public void eliminar(Long id) { // Método para eliminar una cancelación

        log.info("Eliminando cancelación con ID: {}", id); // Log de eliminación

        Cancelacion cancelacion =
                cancelacionRepository.findById(id) // Busca cancelación por ID
                        .orElseThrow(() -> { // Si no existe, lanza excepción
                            log.error(
                                    "No se encontró la cancelación con ID: {}",
                                    id
                            ); // Log de error
                            return new CancelacionNotFoundException(id); // Excepción personalizada
                        });

        cancelacionRepository.delete(cancelacion); // Elimina la cancelación de BD

        log.info(
                "Cancelación eliminada correctamente con ID: {}",
                id
        ); // Log de eliminación correcta
    }

    public List<CancelacionResponse> obtenerPorReserva(
            Long idReserva) { // Busca cancelaciones por ID de reserva

        log.info(
                "Buscando cancelaciones por reserva ID: {}",
                idReserva
        ); // Log de búsqueda por reserva

        return cancelacionMapper.toResponseList( // Convierte lista a Response
                cancelacionRepository.findByIdReserva(idReserva) // Busca en BD por idReserva
        );
    }

    public List<CancelacionResponse> obtenerPorEstado(
            EstadoReembolso estadoReembolso) { // Busca cancelaciones por estado

        log.info(
                "Buscando cancelaciones por estado de reembolso: {}",
                estadoReembolso
        ); // Log de búsqueda por estado

        return cancelacionMapper.toResponseList( // Convierte lista a Response
                cancelacionRepository.findByEstadoReembolso(estadoReembolso) // Busca por estado
        );
    }

    public List<CancelacionResponse> obtenerPorFecha(
            LocalDate fechaCancelacion) { // Busca cancelaciones por fecha

        log.info(
                "Buscando cancelaciones por fecha: {}",
                fechaCancelacion
        ); // Log de búsqueda por fecha

        return cancelacionMapper.toResponseList( // Convierte lista a Response
                cancelacionRepository.findByFechaCancelacion(fechaCancelacion) // Busca por fecha
        );
    }

    private void validarCancelacion(
            CancelacionRequest request) { // Método privado para validar reglas

        if (request.getMotivo() == null
                || request.getMotivo().isBlank()) { // Valida motivo nulo o vacío

            throw new ReglaNegocioException(
                    "El motivo de la cancelación es obligatorio"
            ); // Lanza error si falta motivo
        }

        if (request.getFechaCancelacion() == null) { // Valida fecha nula

            throw new ReglaNegocioException(
                    "La fecha de cancelación es obligatoria"
            ); // Lanza error si falta fecha
        }

        if (request.getFechaCancelacion()
                .isAfter(LocalDate.now())) { // Valida que la fecha no sea futura

            throw new ReglaNegocioException(
                    "La fecha de cancelación no puede ser futura"
            ); // Lanza error si la fecha es futura
        }

        if (request.getIdReserva() == null) { // Valida que venga idReserva

            throw new ReglaNegocioException(
                    "El idReserva es obligatorio"
            ); // Lanza error si falta idReserva
        }
    }
}