package com.ReservaPro.ms_gestion_servicio.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GestionServicioRequest {

    @Schema(description = "Identificador del servicio", example = "1")
    @NotNull(message = "El id del servicio es obligatorio")
    @Min(value = 1, message = "El id del servicio debe ser mayor o igual a 1")
    private Long id;

    @Schema(description = "Nombre del servicio", example = "Arriendo cancha de futbol")
    @NotBlank(message = "El nombre es obligatorio y no puede estar vacío")
    private String nombre;

    @Schema(description = "Precio del servicio", example = "49.99")
    @NotNull(message = "El precio del servicio es obligatorio")
    @Min(value = 0, message = "El precio del servicio debe ser mayor o igual a 0")
    private Double precioServicio;

    @Schema(description = "Duración en minutos del servicio", example = "60")
    @NotNull(message = "El duracionMinuto es obligatorio")
    @Min(value = 1, message = "La duración debe ser al menos 1 minuto")
    @Max(value = 1440, message = "La duración no puede superar 1440 minutos (24 horas)")
    private Integer duracionMinuto;

    @Schema(description = "Estado del servicio (ej. ACTIVO, INACTIVO)", example = "ACTIVO")
    @NotBlank(message = "El estado es obligatorio y no puede estar vacío")
    private String estadoServicio;

    @Schema(description = "Ubicación donde se presta el servicio", example = "Sucursal Centro")
    @NotBlank(message = "El ubicacion es obligatorio y no puede estar vacío")
    private String ubicacion;

    @Schema(description = "Capacidad máxima de personas", example = "10")
    @NotNull(message = "El capacidad es obligatorio")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Max(value = 1000, message = "La capacidad no puede superar 1000")
    private Integer capacidad;

    @Schema(description = "Condiciones o términos del servicio", example = "Se requiere pago anticipado")
    @NotBlank(message = "El condiciones es obligatorio y no puede estar vacío")
    private String condiciones;

    @Schema(description = "Identificador del proveedor", example = "42")
    @NotNull(message = "El proveedorId es obligatorio")
    @Min(value = 1, message = "El proveedorId debe ser mayor o igual a 1")
    private Long proveedorId;
}
