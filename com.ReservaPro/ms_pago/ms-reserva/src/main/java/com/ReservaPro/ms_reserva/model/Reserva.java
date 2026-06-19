package com.ReservaPro.ms_reserva.model;

import com.ReservaPro.ms_reserva.enums.EstadoReserva;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id de la reserva", example = "1")
    private Long id;

    //ids de los otros microservicios

    @Column(name = "id_usuario", nullable = false)
    @Schema(description = "Id del usuario", example = "1")
    private Long idUsuario;

    @Column(name = "id_gestion_servicio", nullable = false)
    @Schema(description = "Id de la gestion del servicio", example = "1")
    private Long idGestionServicio;

    @Column(name = "id_promocion")
    @Schema(description = "Id de la promocion", example = "1")
    private Long idPromocion;

    @Column(name = "id_calificacion")
    @Schema(description = "Id de la Calificacion", example = "1")
    private Long idCalificacion;

    @Column(name = "id_pago")
    @Schema(description = "Id del pago", example = "1")
    private Long idPago;

//Campos del microservicio Reserva

    @Column ( name = "fecha_reserva", nullable = false, length = 30)
    @Schema(description = "Fecha de la reserva", example = "")
    private LocalDateTime FechaReserva;

    @Column ( name = "precio_reserva", nullable = false)
    @Schema(description = "Monto original de la reserva en CLP", example = "1000")
    private Double precioReserva;

    @Column ( name = "descuento_aplicado")
    @Schema(description = "Descuento para aplicar al monto original", example = "1500")
    private Double descuentoAplicado;

    @Column ( name = "precio_final", nullable = false)
    @Schema(description = "Monto final de la reserva (Promocion restada)", example = "500")
    private Double precioFinal;

    @Enumerated(EnumType.STRING)
    @Column ( name = "estado_reserva", nullable = false, length = 20)
    @Schema(description = "Estado de la reserva", example = "PENDIENTE_PAGO/CONFIRMADA/CANCELADA/COMPLETADA")
    private EstadoReserva estadoReserva;

}