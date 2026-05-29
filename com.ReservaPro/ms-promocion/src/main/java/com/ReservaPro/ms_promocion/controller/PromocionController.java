package com.ReservaPro.ms_promocion.controller;

import com.ReservaPro.ms_promocion.dto.request.PromocionRequest;
import com.ReservaPro.ms_promocion.dto.response.CalcularDescuentoResponse;
import com.ReservaPro.ms_promocion.dto.response.PromocionResponse;
import com.ReservaPro.ms_promocion.service.PromocionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promociones")
public class PromocionController {

    private final PromocionService promocionService;

    //CREAR PROMOCIONES
    @PostMapping
    public ResponseEntity<PromocionResponse> crearPromocion(@Valid @RequestBody PromocionRequest promocion){
        PromocionResponse promocionResponse = promocionService.crearPromocion(promocion);

        return ResponseEntity.status(HttpStatus.CREATED).body(promocionResponse);
    }

    // MS-PAGOS
    @GetMapping("/calcular")
    public ResponseEntity<CalcularDescuentoResponse> calcularYActivarPromocion(@RequestParam String codigoPromocion,
                                                                               @RequestParam Double montoOriginal){

        return ResponseEntity.ok(promocionService.calcularYActivarPromocion(codigoPromocion, montoOriginal));
    }

}
