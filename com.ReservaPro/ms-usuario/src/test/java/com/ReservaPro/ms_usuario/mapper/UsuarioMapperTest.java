package com.ReservaPro.ms_usuario.mapper;

import com.ReservaPro.ms_usuario.dto.request.UsuarioRequest;
import com.ReservaPro.ms_usuario.dto.response.UsuarioResponse;
import com.ReservaPro.ms_usuario.enums.RolUsuario;
import com.ReservaPro.ms_usuario.model.Usuario;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    private final UsuarioMapper mapper = new UsuarioMapperImpl();

    @Test
    void toEntity_mapeaCamposYOmiteId() {
        UsuarioRequest request = new UsuarioRequest();
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setPassword("123456");
        request.setEmail("juan@correo.cl");
        request.setRut("12345678-9");
        request.setRol(RolUsuario.CLIENTE);

        Usuario usuario = mapper.toEntity(request);

        assertNotNull(usuario);
        assertNull(usuario.getId());
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Pérez", usuario.getApellido());
        assertEquals("123456", usuario.getPassword());
        assertEquals("juan@correo.cl", usuario.getEmail());
        assertEquals("12345678-9", usuario.getRut());
        assertEquals(RolUsuario.CLIENTE, usuario.getRol());
    }

    @Test
    void toEntity_conNull_retornaNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void toResponse_mapeaCampos() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Pérez")
                .password("123456")
                .email("juan@correo.cl")
                .rut("12345678-9")
                .rol(RolUsuario.ADMINISTRADOR)
                .build();

        UsuarioResponse response = mapper.toResponse(usuario);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Juan", response.getNombre());
        assertEquals("Pérez", response.getApellido());
        assertEquals("juan@correo.cl", response.getEmail());
        assertEquals("12345678-9", response.getRut());
        assertEquals(RolUsuario.ADMINISTRADOR, response.getRol());
    }

    @Test
    void toResponse_conNull_retornaNull() {
        assertNull(mapper.toResponse(null));
    }

    @Test
    void toResponseList_mapeaLista() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan@correo.cl")
                .rut("12345678-9")
                .rol(RolUsuario.CLIENTE)
                .build();

        List<UsuarioResponse> respuestas = mapper.toResponseList(List.of(usuario));

        assertEquals(1, respuestas.size());
        assertEquals("juan@correo.cl", respuestas.get(0).getEmail());
    }

    @Test
    void toResponseList_conNull_retornaNull() {
        assertNull(mapper.toResponseList(null));
    }
}
