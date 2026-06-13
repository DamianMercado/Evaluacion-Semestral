
import com.ReservaPro.ms_gestion_servicio.dto.request.GestionServicioRequest;
import com.ReservaPro.ms_gestion_servicio.dto.response.GestionServicioResponse;
import com.ReservaPro.ms_gestion_servicio.service.GestionServicioService;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/api/v1/Gestion-Servicio")
@RequiredArgsConstructor
@Tag(
        name = "Gestion-Servicio",
        description = "Operaciones relacionadas con la gestion del servicio"
)
public class GestionServicioController {

    private final GestionServicioService gestionServicioService;

    //GET DE TODA LA LISTA
    @GetMapping
    @Operation(
            summary = "Obtener todos los servicios", description = "Retorna una lista de todos los servicios"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    public ResponseEntity<List<GestionServicioResponse>> obtenerServicios() {

        return ResponseEntity.ok(
                gestionServicioService.obtener()
        );
    }

    //GET POR ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener servicios por ID", description = "Obtiene un servicio según su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "servicio encontrado"),
            @ApiResponse(responseCode = "404", description = "servicio no encontrado")
    })
    public ResponseEntity<GestionServicioResponse> obtenerServicio(

            @Parameter(
                    description = "ID del servicio",
                    required = true
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                gestionServicioService.obtenerPorId(id)
        );
    }


    @PostMapping
    @Operation(summary = "Crear un servicio", description = "Crea un nuevo servicio en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Servicio creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<GestionServicioResponse> crearServicio(
            @Valid @RequestBody GestionServicioRequest request) {   // ← solo Spring, sin Swagger

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gestionServicioService.crear(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un servicio", description = "Actualiza un servicio existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio actualizado"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    public ResponseEntity<GestionServicioResponse> actualizarServicio(
            @PathVariable Long id,
            @Valid @RequestBody GestionServicioRequest request) {   // ← igual aquí

        return ResponseEntity.ok(gestionServicioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un servicio", description = "Elimina un servicio por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Servicio eliminada"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    public ResponseEntity<Void> eliminarServicio(

            @Parameter(
                    description = "ID del servicio",
                    required = true
            )
            @PathVariable Long id) {

        gestionServicioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

}