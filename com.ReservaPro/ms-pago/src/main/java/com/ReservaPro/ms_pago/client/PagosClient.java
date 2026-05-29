package com.ReservaPro.ms_pago.client;

import com.ReservaPro.ms_pago.dto.response.CalcularDescuentoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="pagos-services", url = "localhost:8081/api/v1/promociones")
public interface PagosClient {

    @GetMapping("/calcular")
    CalcularDescuentoResponse calcularYActivarPromocion (@RequestParam("codigoPromocion")String codigoPromocion, @RequestParam("montoFinal") Double montoFinal );

}
