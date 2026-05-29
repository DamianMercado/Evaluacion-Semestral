package com.ReservaPro.ms_disponibilidad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "disponibilidades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disponibilidad {

    public enum EstadoDisponibilidad {
        DISPONIBLE,
        BLOQUEADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDisponibilidad;

    @NotNull(message = "La fecha es obligatoria")
    @Column(nullable = false)
    private LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    @Column(nullable = false)
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    @Column(nullable = false)
    private LocalTime horaFin;

    @NotNull(message = "Los cupos disponibles son obligatorios")
    @PositiveOrZero(message = "Los cupos disponibles no pueden ser negativos")
    @Column(nullable = false)
    private Integer cuposDisponibles;

    @NotNull(message = "Los cupos totales son obligatorios")
    @Positive(message = "Debe existir al menos un cupo total")
    @Column(nullable = false)
    private Integer cuposTotales;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoDisponibilidad estado;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;

    @Column(length = 200)
    private String observacion;

    @Column(nullable = false)
    private Boolean activo;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();

        if (this.estado == null) {
            this.estado = EstadoDisponibilidad.DISPONIBLE;
        }

        if (this.activo == null) {
            this.activo = true;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public void crearDisponibilidad() {
        this.estado = EstadoDisponibilidad.DISPONIBLE;
        this.activo = true;
    }

    public void actualizarDisponibilidad(
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            Integer cuposDisponibles
    ) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cuposDisponibles = cuposDisponibles;
    }

    public boolean bloquearCupo(Integer cantidad) {
        if (cantidad <= 0) {
            return false;
        }

        if (this.cuposDisponibles >= cantidad) {
            this.cuposDisponibles -= cantidad;

            if (this.cuposDisponibles == 0) {
                this.estado = EstadoDisponibilidad.BLOQUEADO;
            }

            return true;
        }

        return false;
    }

    public boolean liberarCupo(Integer cantidad) {
        if (cantidad <= 0) {
            return false;
        }

        this.cuposDisponibles += cantidad;

        if (this.cuposDisponibles > 0) {
            this.estado = EstadoDisponibilidad.DISPONIBLE;
        }

        return true;
    }

    public boolean estaDisponible() {
        return this.activo
                && this.estado == EstadoDisponibilidad.DISPONIBLE
                && this.cuposDisponibles > 0;
    }

    public void bloquearDisponibilidad() {
        this.estado = EstadoDisponibilidad.BLOQUEADO;
    }

    public void activarDisponibilidad() {
        this.estado = EstadoDisponibilidad.DISPONIBLE;
        this.activo = true;
    }

    public void desactivarDisponibilidad() {
        this.activo = false;
    }
}