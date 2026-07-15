package com.ReservaPro.ms_gestion_servicio.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Respuesta con la información de un servicio")
public class GestionServicioResponse {

    @Schema(description = "Identificador del servicio", example = "1")
    private Long id;

    @Schema(description = "Nombre del servicio", example = "Arriendo cancha de futbol")
    private String nombre;

    @Schema(description = "Precio del servicio", example = "49.99")
    private Double precioServicio;

    @Schema(description = "Duración en minutos del servicio", example = "60")
    private Integer duracionMinuto;

    @Schema(description = "Estado del servicio", example = "ACTIVADO, DESACTIVADO, MANTENIMIENTO")
    private String estadoServicio;

    @Schema(description = "Ubicación donde se presta el servicio", example = "Sucursal Centro")
    private String ubicacion;

    @Schema(description = "Capacidad máxima de personas", example = "10")
    private Integer capacidad;

    @Schema(description = "Condiciones o términos del servicio", example = "Se requiere pago anticipado")
    private String condiciones;

    @Schema(description = "Identificador del proveedor", example = "42")
    private Long proveedorId;

    @Schema(description = "Fecha de creación del servicio")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de última actualización")
    private LocalDateTime fechaActualizacion;
}