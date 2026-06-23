package com.ReservaPro.ms_calificacion.controller;

import com.ReservaPro.ms_calificacion.dto.request.UsuarioRequest;
import com.ReservaPro.ms_calificacion.dto.response.UsuarioResponse;
import com.ReservaPro.ms_calificacion.enums.RolUsuario;
import com.ReservaPro.ms_calificacion.service.UsuarioService;

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
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(
        name = "Usuarios",
        description = "Operaciones relacionadas con los usuarios"
)
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Retorna una lista de todos los usuarios"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista obtenida correctamente"
    )
    public ResponseEntity<List<UsuarioResponse>> obtenerUsuarios() {

        return ResponseEntity.ok(
                usuarioService.obtener()
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Obtiene un usuario según su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponse> obtenerUsuario(

            @Parameter(
                    description = "ID del usuario",
                    required = true
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                usuarioService.obtenerPorId(id)
        );
    }

    @PostMapping
    @Operation(
            summary = "Crear usuario",
            description = "Crea un nuevo usuario en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<UsuarioResponse> crearUsuario(

            @Valid
            @RequestBody UsuarioRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        usuarioService.crear(request)
                );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza un usuario existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UsuarioResponse> actualizarUsuario(

            @Parameter(
                    description = "ID del usuario",
                    required = true
            )
            @PathVariable Long id,

            @Valid
            @RequestBody UsuarioRequest request) {

        return ResponseEntity.ok(
                usuarioService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminarUsuario(

            @Parameter(
                    description = "ID del usuario",
                    required = true
            )
            @PathVariable Long id) {

        usuarioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/validar-cliente")
    @Operation(
            summary = "Validar cliente",
            description = "Retorna true si el usuario posee rol CLIENTE"
    )
    public ResponseEntity<Boolean> validarCliente(

            @PathVariable Long id) {

        try {

            UsuarioResponse usuario =
                    usuarioService.obtenerPorId(id);

            return ResponseEntity.ok(
                    usuario.getRol() == RolUsuario.CLIENTE
            );

        } catch (Exception e) {

            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/{id}/validar-operador")
    @Operation(
            summary = "Validar operador",
            description = "Retorna true si el usuario posee rol OPERADOR_SERVICIO"
    )
    public ResponseEntity<Boolean> validarOperador(

            @PathVariable Long id) {

        try {

            UsuarioResponse usuario =
                    usuarioService.obtenerPorId(id);

            return ResponseEntity.ok(
                    usuario.getRol() == RolUsuario.OPERADOR_SERVICIO
            );

        } catch (Exception e) {

            return ResponseEntity.ok(false);
        }
    }
}