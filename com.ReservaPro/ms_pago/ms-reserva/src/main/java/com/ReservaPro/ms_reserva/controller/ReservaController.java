package com.ReservaPro.ms_reserva.controller;

import com.ReservaPro.ms_reserva.dto.request.ReservaRequest;
import com.ReservaPro.ms_reserva.dto.response.ReservaResponse;
import com.ReservaPro.ms_reserva.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
@Tag(
        name = "Reservas",
        description = "Operaciones relacionadas con las reservas"
)
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    @Operation(
            summary = "Obtener todos las reservas",
            description = "Retorna una lista de todos las reservas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista obtenida correctamente"
    )
    public ResponseEntity<List<ReservaResponse>> obtenerReservas() {

        return ResponseEntity.ok(
                reservaService.obtener()
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener reserva por ID",
            description = "Obtiene una reserva según su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<ReservaResponse> obtenerReserva(

            @Parameter(
                    description = "ID de la Reserva",
                    required = true
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservaService.obtenerPorId(id)
        );
    }

    @PostMapping
    @Operation(
            summary = "Crear reserva",
            description = "Crea una nueva reserva en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva se ha creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ReservaResponse> crearReserva(

            @Valid
            @RequestBody ReservaRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        reservaService.crear(request)
                );

    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar reserva",
            description = "Actualiza una reserva existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva actualizada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<ReservaResponse> actualizarReserva(

            @Parameter(
                    description = "ID del usuario",
                    required = true
            )
            @PathVariable Long id,

            @Valid
            @RequestBody ReservaRequest request) {

        return ResponseEntity.ok(
                reservaService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar reserva",
            description = "Elimina una reserva por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva eliminada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<Void> eliminarReserva(

            @Parameter(
                    description = "ID de la reserva",
                    required = true
            )
            @PathVariable Long id) {

        reservaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}

//Crear Validaciones

//    @GetMapping("/{id}/validar-operador")
//    @Operation(
//            summary = "Validar operador",
//            description = "Retorna true si el usuario posee rol OPERADOR_SERVICIO"
//    )
//    public ResponseEntity<Boolean> validarOperador(
//
//          @PathVariable Long id) {
//
//      try {
//
//          UsuarioResponse usuario =
//                  usuarioService.obtenerPorId(id);
//
//          return ResponseEntity.ok(
//                  usuario.getRol() == RolUsuario.OPERADOR_SERVICIO
//          );
//
//      } catch (Exception e) {
//
//          return ResponseEntity.ok(false);
//      }
//  }
//}