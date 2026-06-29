package com.ReservaPro.ms_cancelacion.model;
// Define el paquete donde se encuentra la entidad Cancelacion

import io.swagger.v3.oas.annotations.media.Schema;
// Permite documentar la clase y atributos en Swagger

import jakarta.persistence.*;
// Importa las anotaciones de JPA para mapear la entidad a la base de datos

import jakarta.validation.constraints.NotBlank;
// Valida que un texto no sea nulo ni vacío

import jakarta.validation.constraints.NotNull;
// Valida que un atributo no sea nulo

import lombok.*;
// Importa todas las anotaciones de Lombok

import java.time.LocalDate;
// Permite trabajar con fechas

@Entity
// Indica que esta clase es una entidad JPA y se almacenará en una tabla

@Table(name = "cancelaciones")
// Indica que la entidad se guardará en la tabla "cancelaciones"

@Data
// Genera automáticamente getters, setters, toString, equals y hashCode

@NoArgsConstructor
// Genera un constructor vacío

@AllArgsConstructor
// Genera un constructor con todos los atributos

@Builder
// Permite crear objetos usando el patrón Builder

@Schema(description = "Entidad que representa una cancelación de reserva")
// Descripción general de la entidad en Swagger

public class Cancelacion {

    @Id
    // Indica que este atributo es la clave primaria (Primary Key)

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // El ID se genera automáticamente de forma incremental en la BD

    @Schema(description = "ID único de la cancelación", example = "1")
    // Documenta el atributo en Swagger

    private Long idCancelacion;
    // Identificador único de la cancelación

    @NotBlank(message = "El motivo es obligatorio")
    // Valida que el motivo no sea nulo ni vacío

    @Column(nullable = false)
    // Crea una columna obligatoria en la BD

    @Schema(
            description = "Motivo de la cancelación",
            example = "Cambio de planes"
    )
    // Muestra descripción y ejemplo en Swagger

    private String motivo;
    // Guarda el motivo de la cancelación

    @NotNull(message = "La fecha de cancelación es obligatoria")
    // Valida que la fecha no sea nula

    @Column(nullable = false)
    // La columna es obligatoria en la BD

    @Schema(
            description = "Fecha de la cancelación",
            example = "2026-06-10"
    )
    // Documenta el atributo en Swagger

    private LocalDate fechaCancelacion;
    // Guarda la fecha en que se realizó la cancelación

    @Enumerated(EnumType.STRING)
    // Guarda el Enum como texto en la BD y no como número

    @Column(nullable = false)
    // El estado es obligatorio

    @Schema(
            description = "Estado del reembolso",
            example = "PENDIENTE"
    )
    // Muestra información del atributo en Swagger

    private EstadoReembolso estadoReembolso;
    // Guarda el estado del reembolso (PENDIENTE o REEMBOLSADO)

    @Column(name = "id_reserva", nullable = false)
    // Crea la columna id_reserva obligatoria en la BD

    @Schema(
            description = "ID de la reserva asociada",
            example = "123"
    )
    // Documenta el atributo en Swagger

    private Long idReserva;
    // Guarda el ID de la reserva asociada a la cancelación
}