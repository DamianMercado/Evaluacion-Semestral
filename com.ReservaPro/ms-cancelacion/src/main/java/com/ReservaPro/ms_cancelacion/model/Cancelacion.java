package com.ReservaPro.ms_cancelacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cancelaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cancelacion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCancelacion;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @NotBlank(message = "La fecha de cancelacion es obligatoria")
    private String fechaCancelacion;

    @Enumerated(EnumType.STRING)
    private EstadoReembolso estadoReembolso;

    public void cancelarReserva(String motivo) {
        this.motivo = motivo;
        this.estadoReembolso = EstadoReembolso.PENDIENTE;
    }

    public boolean procesarReembolso() {
        this.estadoReembolso = EstadoReembolso.REEMBOLSADO;
        return true;
    }
}



