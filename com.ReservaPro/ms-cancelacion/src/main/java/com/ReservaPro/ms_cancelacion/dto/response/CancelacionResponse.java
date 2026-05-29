package com.ReservaPro.ms_cancelacion.dto.response;

import com.ReservaPro.ms_cancelacion.model.EstadoReembolso;
import lombok.Data;

@Data
public class CancelacionResponse {

    private Long idCancelacion;
    private String motivo;
    private String fechaCancelacion;
    private EstadoReembolso estadoReembolso;
}