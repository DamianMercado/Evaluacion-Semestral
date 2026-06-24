package com.ReservaPro.ms_pago.client;

import com.ReservaPro.ms_pago.dto.response.CalcularDescuentoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-promocion")
public interface PromocionClient {

    @PostMapping("/api/v1/promociones/aplicar")
    CalcularDescuentoResponse aplicarPromocion(
            @RequestParam("codigoPromocion") String codigoPromocion,
            @RequestParam("montoFinal") Double montoFinal
    );
}