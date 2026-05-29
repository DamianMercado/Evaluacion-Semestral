package com.ReservaPro.ms_cancelacion.dto.request;

import com.ReservaPro.ms_cancelacion.model.EstadoReembolso;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CancelacionRequest {

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @NotBlank(message = "La fecha de cancelacion es obligatoria")
    private String fechaCancelacion;

    private EstadoReembolso estadoReembolso;
}
