package com.ReservaPro.ms_notificacion.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NotificacionRequest {

    @NotNull(message = "El idUsuario es obligatorio")
    private Long idUsuario;


    private Long idReserva;


    private Long idCancelacion;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 1, max = 200,
            message = "El mensaje debe tener entre 1 y 200 caracteres")
    private String mensaje;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(min = 1, max = 20,
            message = "El tipo debe tener entre 1 y 20 caracteres")
    private String tipo;

    @NotNull(message = "El campo leida es obligatorio")
    private Boolean leida;
}