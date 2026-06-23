package com.ReservaPro.ms_pago.client;

import com.ReservaPro.ms_reserva.dto.request.ReservaRequest;
import com.ReservaPro.ms_reserva.dto.response.ReservaResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "ms-reserva", url = "http://localhost:8084")
public interface ReservaClient {
    @PutMapping("/api/v1/reservas/{id}")
    ReservaResponse actualizarReserva(@PathVariable Long id, @RequestBody ReservaRequest request);
}
