package com.ReservaPro.ms_gestion_servicio.service;
import com.ReservaPro.ms_gestion_servicio.dto.request.GestionServicioRequest;
import com.ReservaPro.ms_gestion_servicio.dto.response.GestionServicioResponse;
import com.ReservaPro.ms_gestion_servicio.enums.EstadoServicio;
import com.ReservaPro.ms_gestion_servicio.exception.GestionServicioNotFoundException;
import com.ReservaPro.ms_gestion_servicio.mapper.GestionServicioMapper;
import com.ReservaPro.ms_gestion_servicio.model.GestionServicio;
import com.ReservaPro.ms_gestion_servicio.repository.GestionServicioRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

public class GestionServicioServiceTests {



        // Simula el Repository para no usar una base de datos real
        @Mock
        private GestionServicioRepository gestionServicioRepository;

        // Simula las conversiones realizadas por MapStruct
        @Mock
        private GestionServicioMapper gestionServicioMapper;

        // Crea el Service real e inyecta los mocks
        @InjectMocks
        private GestionServicioService gestionServicioService;

        // =========================================================
        // OBTENER TODOS
        // =========================================================

        @Test
        void obtenerTodos_cuandoExistenServicios_retornaLista() {

            // Given
            GestionServicio servicio = crearServicio(1L);
            GestionServicioResponse response = crearResponse(1L);

            List<GestionServicio> servicios = List.of(servicio);
            List<GestionServicioResponse> responses = List.of(response);

            when(gestionServicioRepository.findAll())
                    .thenReturn(servicios);

            when(gestionServicioMapper.toResponseList(servicios))
                    .thenReturn(responses);

            // When
            List<GestionServicioResponse> resultado =
                    gestionServicioService.obtenerTodos();

            // Then
            assertNotNull(resultado);
            assertFalse(resultado.isEmpty());
            assertEquals(1, resultado.size());
            assertEquals(1L, resultado.get(0).getId());
            assertEquals(
                    "Arriendo cancha de futbol",
                    resultado.get(0).getNombre()
            );

            verify(gestionServicioRepository).findAll();
            verify(gestionServicioMapper).toResponseList(servicios);
        }

        @Test
        void obtenerTodos_cuandoNoExistenServicios_retornaListaVacia() {

            // Given
            when(gestionServicioRepository.findAll())
                    .thenReturn(List.of());

            when(gestionServicioMapper.toResponseList(List.of()))
                    .thenReturn(List.of());

            // When
            List<GestionServicioResponse> resultado =
                    gestionServicioService.obtenerTodos();

            // Then
            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());

            verify(gestionServicioRepository).findAll();
        }

        // =========================================================
        // OBTENER POR ID
        // =========================================================

        @Test
        void obtenerPorId_cuandoExiste_retornaServicio() {

            // Given
            Long id = 1L;

            GestionServicio servicio = crearServicio(id);
            GestionServicioResponse response = crearResponse(id);

            when(gestionServicioRepository.findById(id))
                    .thenReturn(Optional.of(servicio));

            when(gestionServicioMapper.toResponse(servicio))
                    .thenReturn(response);

            // When
            GestionServicioResponse resultado =
                    gestionServicioService.obtenerPorId(id);

            // Then
            assertNotNull(resultado);
            assertEquals(id, resultado.getId());
            assertEquals(
                    "Arriendo cancha de futbol",
                    resultado.getNombre()
            );
            assertEquals(49.99, resultado.getPrecioServicio());

            verify(gestionServicioRepository).findById(id);
            verify(gestionServicioMapper).toResponse(servicio);
        }

        @Test
        void obtenerPorId_cuandoNoExiste_lanzaException() {

            // Given
            Long id = 99L;

            when(gestionServicioRepository.findById(id))
                    .thenReturn(Optional.empty());

            // When y Then
            assertThrows(
                    GestionServicioNotFoundException.class,
                    () -> gestionServicioService.obtenerPorId(id)
            );

            verify(gestionServicioRepository).findById(id);

            verify(
                    gestionServicioMapper,
                    never()
            ).toResponse(any(GestionServicio.class));
        }

        // =========================================================
        // OBTENER POR ESTADO
        // =========================================================

        @Test
        void obtenerPorEstado_cuandoExisten_retornaLista() {

            // Given
            EstadoServicio estado = EstadoServicio.ACTIVADO;

            GestionServicio servicio = crearServicio(1L);
            GestionServicioResponse response = crearResponse(1L);

            List<GestionServicio> servicios = List.of(servicio);
            List<GestionServicioResponse> responses = List.of(response);

            when(gestionServicioRepository.findByEstadoServicio(estado))
                    .thenReturn(servicios);

            when(gestionServicioMapper.toResponseList(servicios))
                    .thenReturn(responses);

            // When
            List<GestionServicioResponse> resultado =
                    gestionServicioService.obtenerPorEstado(estado);

            // Then
            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            assertEquals(
                    EstadoServicio.ACTIVADO.getValor(),
                    resultado.get(0).getEstadoServicio()
            );

            verify(gestionServicioRepository)
                    .findByEstadoServicio(estado);

            verify(gestionServicioMapper)
                    .toResponseList(servicios);
        }

        // =========================================================
        // BUSCAR POR NOMBRE
        // =========================================================

        @Test
        void buscarPorNombre_cuandoCoincide_retornaLista() {

            // Given
            String nombre = "cancha";

            GestionServicio servicio = crearServicio(1L);
            GestionServicioResponse response = crearResponse(1L);

            List<GestionServicio> servicios = List.of(servicio);
            List<GestionServicioResponse> responses = List.of(response);

            when(gestionServicioRepository
                    .findByNombreContainingIgnoreCase(nombre))
                    .thenReturn(servicios);

            when(gestionServicioMapper.toResponseList(servicios))
                    .thenReturn(responses);

            // When
            List<GestionServicioResponse> resultado =
                    gestionServicioService.buscarPorNombre(nombre);

            // Then
            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            assertEquals(
                    "Arriendo cancha de futbol",
                    resultado.get(0).getNombre()
            );

            verify(gestionServicioRepository)
                    .findByNombreContainingIgnoreCase(nombre);
        }

        // =========================================================
        // OBTENER POR PROVEEDOR
        // =========================================================

        @Test
        void obtenerPorProveedor_cuandoExisten_retornaLista() {

            // Given
            Long proveedorId = 42L;

            GestionServicio servicio = crearServicio(1L);
            GestionServicioResponse response = crearResponse(1L);

            List<GestionServicio> servicios = List.of(servicio);
            List<GestionServicioResponse> responses = List.of(response);

            when(gestionServicioRepository
                    .findByProveedorId(proveedorId))
                    .thenReturn(servicios);

            when(gestionServicioMapper.toResponseList(servicios))
                    .thenReturn(responses);

            // When
            List<GestionServicioResponse> resultado =
                    gestionServicioService.obtenerPorProveedor(proveedorId);

            // Then
            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            assertEquals(
                    proveedorId,
                    resultado.get(0).getProveedorId()
            );

            verify(gestionServicioRepository)
                    .findByProveedorId(proveedorId);
        }

        // =========================================================
        // CREAR
        // =========================================================

        @Test
        void crear_cuandoNombreNoExiste_guardaServicio() {

            // Given
            GestionServicioRequest request =
                    mock(GestionServicioRequest.class);

            GestionServicio servicioNuevo =
                    crearServicio(null);

            // Se deja null para comprobar el estado por defecto
            servicioNuevo.setEstadoServicio(null);

            GestionServicio servicioGuardado =
                    crearServicio(1L);

            GestionServicioResponse response =
                    crearResponse(1L);

            when(request.getNombre())
                    .thenReturn("Arriendo cancha de futbol");

            when(gestionServicioRepository.existsByNombreIgnoreCase(
                    "Arriendo cancha de futbol"
            )).thenReturn(false);

            when(gestionServicioMapper.toEntity(request))
                    .thenReturn(servicioNuevo);

            when(gestionServicioRepository.save(servicioNuevo))
                    .thenReturn(servicioGuardado);

            when(gestionServicioMapper.toResponse(servicioGuardado))
                    .thenReturn(response);

            // When
            GestionServicioResponse resultado =
                    gestionServicioService.crear(request);

            // Then
            assertNotNull(resultado);
            assertEquals(1L, resultado.getId());
            assertEquals(
                    "Arriendo cancha de futbol",
                    resultado.getNombre()
            );

            // El Service asigna ACTIVADO cuando el estado viene null
            assertEquals(
                    EstadoServicio.ACTIVADO,
                    servicioNuevo.getEstadoServicio()
            );

            verify(gestionServicioRepository)
                    .existsByNombreIgnoreCase(
                            "Arriendo cancha de futbol"
                    );

            verify(gestionServicioMapper).toEntity(request);
            verify(gestionServicioRepository).save(servicioNuevo);
            verify(gestionServicioMapper).toResponse(servicioGuardado);
        }

        @Test
        void crear_cuandoNombreYaExiste_lanzaException() {

            // Given
            GestionServicioRequest request =
                    mock(GestionServicioRequest.class);

            when(request.getNombre())
                    .thenReturn("Arriendo cancha de futbol");

            when(gestionServicioRepository.existsByNombreIgnoreCase(
                    "Arriendo cancha de futbol"
            )).thenReturn(true);

            // When
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> gestionServicioService.crear(request)
                    );

            // Then
            assertEquals(
                    "Ya existe un servicio con el nombre: " +
                            "Arriendo cancha de futbol",
                    exception.getMessage()
            );

            verify(
                    gestionServicioMapper,
                    never()
            ).toEntity(any(GestionServicioRequest.class));

            verify(
                    gestionServicioRepository,
                    never()
            ).save(any(GestionServicio.class));
        }

        // =========================================================
        // ACTUALIZAR
        // =========================================================

        @Test
        void actualizar_cuandoExiste_actualizaServicio() {

            // Given
            Long id = 1L;

            GestionServicioRequest request =
                    mock(GestionServicioRequest.class);

            GestionServicio servicioExistente =
                    crearServicio(id);

            GestionServicioResponse responseActualizado =
                    crearResponse(id);

            responseActualizado.setNombre(
                    "Cancha de futbol actualizada"
            );
            responseActualizado.setPrecioServicio(59.99);
            responseActualizado.setCapacidad(20);

            when(gestionServicioRepository.findById(id))
                    .thenReturn(Optional.of(servicioExistente));

            when(request.getNombre())
                    .thenReturn("Cancha de futbol actualizada");

            when(request.getPrecioServicio())
                    .thenReturn(59.99);

            when(request.getDuracionMinuto())
                    .thenReturn(90);

            when(request.getEstadoServicio())
                    .thenReturn("ACTIVADO");

            when(request.getUbicacion())
                    .thenReturn("Sucursal Viña del Mar");

            when(request.getCapacidad())
                    .thenReturn(20);

            when(request.getCondiciones())
                    .thenReturn("Pago anticipado obligatorio");

            when(request.getProveedorId())
                    .thenReturn(42L);

            when(gestionServicioRepository.existsByNombreIgnoreCase(
                    "Cancha de futbol actualizada"
            )).thenReturn(false);

            when(gestionServicioMapper.stringToEstadoServicio(
                    "ACTIVADO"
            )).thenReturn(EstadoServicio.ACTIVADO);

            when(gestionServicioRepository.save(servicioExistente))
                    .thenReturn(servicioExistente);

            when(gestionServicioMapper.toResponse(servicioExistente))
                    .thenReturn(responseActualizado);

            // When
            GestionServicioResponse resultado =
                    gestionServicioService.actualizar(id, request);

            // Then
            assertNotNull(resultado);
            assertEquals(id, resultado.getId());
            assertEquals(
                    "Cancha de futbol actualizada",
                    resultado.getNombre()
            );
            assertEquals(59.99, resultado.getPrecioServicio());
            assertEquals(20, resultado.getCapacidad());

            assertEquals(
                    "Cancha de futbol actualizada",
                    servicioExistente.getNombre()
            );
            assertEquals(90, servicioExistente.getDuracionMinuto());
            assertEquals(
                    EstadoServicio.ACTIVADO,
                    servicioExistente.getEstadoServicio()
            );

            verify(gestionServicioRepository).findById(id);
            verify(gestionServicioRepository).save(servicioExistente);
            verify(gestionServicioMapper).toResponse(servicioExistente);
        }

        @Test
        void actualizar_cuandoNoExiste_lanzaException() {

            // Given
            Long id = 99L;

            GestionServicioRequest request =
                    mock(GestionServicioRequest.class);

            when(gestionServicioRepository.findById(id))
                    .thenReturn(Optional.empty());

            // When y Then
            assertThrows(
                    GestionServicioNotFoundException.class,
                    () -> gestionServicioService.actualizar(id, request)
            );

            verify(gestionServicioRepository).findById(id);

            verify(
                    gestionServicioRepository,
                    never()
            ).save(any(GestionServicio.class));
        }

        @Test
        void actualizar_cuandoNuevoNombreYaExiste_lanzaException() {

            // Given
            Long id = 1L;

            GestionServicioRequest request =
                    mock(GestionServicioRequest.class);

            GestionServicio servicioExistente =
                    crearServicio(id);

            when(gestionServicioRepository.findById(id))
                    .thenReturn(Optional.of(servicioExistente));

            when(request.getNombre())
                    .thenReturn("Servicio repetido");

            when(gestionServicioRepository.existsByNombreIgnoreCase(
                    "Servicio repetido"
            )).thenReturn(true);

            // When
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> gestionServicioService
                                    .actualizar(id, request)
                    );

            // Then
            assertEquals(
                    "Ya existe un servicio con el nombre: Servicio repetido",
                    exception.getMessage()
            );

            verify(
                    gestionServicioRepository,
                    never()
            ).save(any(GestionServicio.class));
        }

        // =========================================================
        // ELIMINAR LÓGICAMENTE
        // =========================================================

        @Test
        void eliminar_cuandoExiste_cambiaEstadoAEliminado() {

            // Given
            Long id = 1L;

            GestionServicio servicio =
                    crearServicio(id);

            when(gestionServicioRepository.findById(id))
                    .thenReturn(Optional.of(servicio));

            when(gestionServicioRepository.save(servicio))
                    .thenReturn(servicio);

            // When
            gestionServicioService.eliminar(id);

            // Then
            assertEquals(
                    EstadoServicio.ELIMINADO,
                    servicio.getEstadoServicio()
            );

            verify(gestionServicioRepository).findById(id);
            verify(gestionServicioRepository).save(servicio);

            // Verifica que no se eliminó físicamente
            verify(
                    gestionServicioRepository,
                    never()
            ).delete(any(GestionServicio.class));

            verify(
                    gestionServicioRepository,
                    never()
            ).deleteById(anyLong());
        }

        @Test
        void eliminar_cuandoNoExiste_lanzaException() {

            // Given
            Long id = 99L;

            when(gestionServicioRepository.findById(id))
                    .thenReturn(Optional.empty());

            // When y Then
            assertThrows(
                    GestionServicioNotFoundException.class,
                    () -> gestionServicioService.eliminar(id)
            );

            verify(gestionServicioRepository).findById(id);

            verify(
                    gestionServicioRepository,
                    never()
            ).save(any(GestionServicio.class));
        }

        // =========================================================
        // CAMBIAR ESTADO
        // =========================================================

        @Test
        void cambiarEstado_cuandoExiste_modificaEstado() {

            // Given
            Long id = 1L;

            GestionServicio servicio =
                    crearServicio(id);

            GestionServicioResponse response =
                    crearResponse(id);

            response.setEstadoServicio(
                    EstadoServicio.ELIMINADO.getValor()
            );

            when(gestionServicioRepository.findById(id))
                    .thenReturn(Optional.of(servicio));

            when(gestionServicioRepository.save(servicio))
                    .thenReturn(servicio);

            when(gestionServicioMapper.toResponse(servicio))
                    .thenReturn(response);

            // When
            GestionServicioResponse resultado =
                    gestionServicioService.cambiarEstado(
                            id,
                            EstadoServicio.ELIMINADO
                    );

            // Then
            assertNotNull(resultado);

            assertEquals(
                    EstadoServicio.ELIMINADO,
                    servicio.getEstadoServicio()
            );

            assertEquals(
                    EstadoServicio.ELIMINADO.getValor(),
                    resultado.getEstadoServicio()
            );

            verify(gestionServicioRepository).findById(id);
            verify(gestionServicioRepository).save(servicio);
            verify(gestionServicioMapper).toResponse(servicio);
        }

        @Test
        void cambiarEstado_cuandoNoExiste_lanzaException() {

            // Given
            Long id = 99L;

            when(gestionServicioRepository.findById(id))
                    .thenReturn(Optional.empty());

            // When y Then
            assertThrows(
                    GestionServicioNotFoundException.class,
                    () -> gestionServicioService.cambiarEstado(
                            id,
                            EstadoServicio.ACTIVADO
                    )
            );

            verify(gestionServicioRepository).findById(id);

            verify(
                    gestionServicioRepository,
                    never()
            ).save(any(GestionServicio.class));
        }

        // =========================================================
        // MÉTODO AUXILIAR: CREA UNA ENTIDAD
        // =========================================================

        private GestionServicio crearServicio(Long id) {

            GestionServicio servicio =
                    new GestionServicio();

            servicio.setId(id);
            servicio.setNombre(
                    "Arriendo cancha de futbol"
            );
            servicio.setPrecioServicio(49.99);
            servicio.setDuracionMinuto(60);
            servicio.setEstadoServicio(
                    EstadoServicio.ACTIVADO
            );
            servicio.setUbicacion(
                    "Sucursal Centro"
            );
            servicio.setCapacidad(10);
            servicio.setCondiciones(
                    "Se requiere pago anticipado"
            );
            servicio.setProveedorId(42L);
            servicio.setFechaCreacion(
                    LocalDateTime.of(
                            2026, 6, 29, 10, 0
                    )
            );
            servicio.setFechaActualizacion(
                    LocalDateTime.of(
                            2026, 6, 29, 10, 0
                    )
            );

            return servicio;
        }

        // =========================================================
        // MÉTODO AUXILIAR: CREA UN RESPONSE
        // =========================================================

        private GestionServicioResponse crearResponse(Long id) {

            GestionServicioResponse response =
                    new GestionServicioResponse();

            response.setId(id);
            response.setNombre(
                    "Arriendo cancha de futbol"
            );
            response.setPrecioServicio(49.99);
            response.setDuracionMinuto(60);
            response.setEstadoServicio(
                    EstadoServicio.ACTIVADO.getValor()
            );
            response.setUbicacion(
                    "Sucursal Centro"
            );
            response.setCapacidad(10);
            response.setCondiciones(
                    "Se requiere pago anticipado"
            );
            response.setProveedorId(42L);
            response.setFechaCreacion(
                    LocalDateTime.of(
                            2026, 6, 29, 10, 0
                    )
            );
            response.setFechaActualizacion(
                    LocalDateTime.of(
                            2026, 6, 29, 10, 0
                    )
            );

            return response;
        }
    }

