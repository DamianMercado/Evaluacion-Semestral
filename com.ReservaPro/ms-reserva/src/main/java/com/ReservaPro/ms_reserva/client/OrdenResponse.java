package com.ReservaPro.ms_reserva.client;
import com.ReservaPro.ms_reserva.model.Usuario;
import lombok.*;
import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdenResponse {

    private Long id;
    private Usuario usuario;
    private Integer total;
    private LocalDateTime fechaCreacion;
    private String estado;
}
