package com.ReservaPro.ms_pago.controller;

import com.ReservaPro.ms_pago.dto.request.PagoRequest;
import com.ReservaPro.ms_pago.dto.response.EstadoResponse;
import com.ReservaPro.ms_pago.dto.response.MontoResponse;
import com.ReservaPro.ms_pago.dto.response.PagoResponse;
import com.ReservaPro.ms_pago.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pagos")
public class PagoController {

    private final PagoService pagoService;

    //CREAR_PAGO
    @PostMapping
    public ResponseEntity<PagoResponse> procesarPago (@Valid @RequestBody PagoRequest pago){
        PagoResponse pagoResponse = pagoService.procesarPago(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoResponse);
    }
    //ACTUALIZAR CONFIRMAR_PAGO
    @PutMapping("/validar/{id}")
    public ResponseEntity<PagoResponse> validarPago (@PathVariable Long id){
        return ResponseEntity.ok(pagoService.validarPago(id));
    }
    //ACTUALIZAR REEMBOLSAR_PAGO
    @PutMapping("/reembolsar/{id}")
    public ResponseEntity<PagoResponse> reembolsarPago (@PathVariable Long id){
        return ResponseEntity.ok(pagoService.reembolsarPago(id));
    }
    //OBTENER ESTADO_PAGO
    @GetMapping("/estado/{id}")
    public ResponseEntity<EstadoResponse> verEstadoPago (@PathVariable Long id){
        return ResponseEntity.ok(pagoService.verEstadoPago(id));
    }
    //OBTENER MONTO_PAGO
    @GetMapping("/monto/{id}")
    public ResponseEntity<MontoResponse> verMontoPago (@PathVariable Long id){
        return ResponseEntity.ok(pagoService.verMontoPago(id));
    }
}
