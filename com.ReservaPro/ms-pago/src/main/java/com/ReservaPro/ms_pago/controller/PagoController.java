package com.ReservaPro.ms_pago.controller;

import com.ReservaPro.ms_pago.dto.request.PagoRequest;
import com.ReservaPro.ms_pago.dto.response.EstadoResponse;
import com.ReservaPro.ms_pago.dto.response.MontoResponse;
import com.ReservaPro.ms_pago.dto.response.PagoResponse;
import com.ReservaPro.ms_pago.model.Pago;
import com.ReservaPro.ms_pago.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pagos")
@Tag(name="Pagos", description = "Operaciones relacionados con los pagos")
public class PagoController {

    private final PagoService pagoService;

    //LISTAR PAGO
    @GetMapping
    @Operation(summary = "Obtener todos los pagos", description = "Obtiene una lista de pagos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "429", description = "Ha superado el tiempo limite de peticiones")
    })
    public ResponseEntity <List<Pago>> listarPagos(){
        return ResponseEntity.ok().body(pagoService.listarPagos());
    }

    //CREAR_PAGO
    @PostMapping
    @Operation(summary = "Creando un nuevo pago", description = "Se crea un pago en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Se esta procesando la solicitud"),
            @ApiResponse(responseCode = "402", description = "Se requiere pagar primero")
    })
    public ResponseEntity<PagoResponse> procesarPago (@Valid @RequestBody PagoRequest pago){
        PagoResponse pagoResponse = pagoService.procesarPago(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoResponse);
    }

    //ELIMINAR PAGO
    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "eliminar pago", description = "Elminar un pago a través de su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<Void> eliminarPago (@PathVariable Long id){
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }

    // CONFIRMAR_PAGO
    @PutMapping("/validar/{id}")
    @Operation(summary = "Validar pago", description = "Corroborar si el pago es válido según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Actualización exitosa"),
            @ApiResponse(responseCode = "400", description = "Datos no válidos")
    })
    public ResponseEntity<PagoResponse> validarPago (@PathVariable Long id){
        return ResponseEntity.ok(pagoService.validarPago(id));
    }
    //ACTUALIZAR REEMBOLSAR_PAGO
    @PutMapping("/reembolsar/{id}")
    @Operation(summary = "Reembolsar un pago", description = "Reembolsar el pago si el usuario cancela la compra ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Actualización exitosa"),
            @ApiResponse(responseCode = "402", description = "El pago no se puede reembolsar"),
            @ApiResponse(responseCode = "404", description = "Datos no existe en el sistema")
    })
    public ResponseEntity<?> reembolsarPago (@PathVariable Long id){
        try {
            PagoResponse pagoResponse = pagoService.reembolsarPago(id);
            return ResponseEntity.ok(pagoResponse);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El pago con ID" + id +"no exixte");
        }
    }
    //OBTENER ESTADO_PAGO
    @GetMapping("/estado/{id}")
    @Operation(summary = "Obtener el estado del pago", description = "Obtiene el estado de un pago a través de la ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "No hay pagos que mostrar")
    })
    public ResponseEntity<EstadoResponse> verEstadoPago (@PathVariable Long id){
        return ResponseEntity.ok(pagoService.verEstadoPago(id));
    }
    //OBTENER MONTO_PAGO
    @GetMapping("/monto/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "No hay monto que mostrar")
    })
    public ResponseEntity<MontoResponse> verMontoPago (@PathVariable Long id){
        return ResponseEntity.ok(pagoService.verMontoPago(id));
    }
}
