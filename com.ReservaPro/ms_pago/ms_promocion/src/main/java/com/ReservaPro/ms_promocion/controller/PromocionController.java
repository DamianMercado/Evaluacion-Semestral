package com.ReservaPro.ms_promocion.controller;

import com.ReservaPro.ms_promocion.dto.request.PromocionRequest;
import com.ReservaPro.ms_promocion.dto.response.CalcularDescuentoResponse;
import com.ReservaPro.ms_promocion.dto.response.PromocionResponse;
import com.ReservaPro.ms_promocion.exception.PromocionInactivaException;
import com.ReservaPro.ms_promocion.exception.PromocionNoEncontradaException;
import com.ReservaPro.ms_promocion.model.Promocion;
import com.ReservaPro.ms_promocion.service.PromocionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promociones")
public class PromocionController {

    public final PromocionService promocionService;

    //LISTAR PROMOCIONES
    @GetMapping
    @Operation(summary = "Obtener todas las promociones", description = "Obtiene una lista de promciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "429", description = "Ha superado el tiempo limite de peticiones")
    })
    public ResponseEntity <List<Promocion>> obtenerTodasLasPromociones() {
        return ResponseEntity.ok().body(promocionService.ListarPromociones());
    }

    //CREAR PROMOCIONES
    @PostMapping
    @Operation(summary = "Crear una nueva promoción", description = "Registra una nueva promoción en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Promoción creada exitosamente")
    })
    public ResponseEntity<PromocionResponse> crearPromocion(@Valid @RequestBody PromocionRequest promocion){
        PromocionResponse promocionResponse = promocionService.crearPromocion(promocion);

        return ResponseEntity.status(HttpStatus.CREATED).body(promocionResponse);
    }

    //APLICA LA PROMOCION
    @PostMapping("/aplicar")
    @Operation(summary = "Aplicar promoción", description = "Calcula el descuento de una compra y marca la promoción como usada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promoción aplicada exitosamente"),
            @ApiResponse(responseCode = "400", description = "La promoción no se puede usar"),
            @ApiResponse(responseCode = "404", description = "El código de promoción no existe")
    })
    public ResponseEntity<?> calcularYActivarPromocion (@RequestParam String codigoPromocion,@RequestParam Double montoFinal){
        try{
            CalcularDescuentoResponse calcularDescuentoResponse = promocionService.calcularYActivarPromocion(codigoPromocion, montoFinal);
            return ResponseEntity.ok(calcularDescuentoResponse);
        }catch (PromocionInactivaException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }catch (PromocionNoEncontradaException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}
