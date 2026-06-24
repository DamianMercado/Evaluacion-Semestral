package com.ReservaPro.ms_pago.client;

import com.ReservaPro.ms_pago.dto.request.ReservaPagoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-reserva")
public interface ReservaClient {

    @PatchMapping("/api/v1/reservas/{id}/pagar")
    Object confirmarPagoReserva(
            @PathVariable("id") Long idReserva,
            @RequestBody ReservaPagoRequest request
    );
}