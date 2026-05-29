package com.ReservaPro.ms_usuario.controller;
import com.ReservaPro.ms_usuario.enums.RolUsuario;
import com.ReservaPro.ms_usuario.model.Usuario;
import com.ReservaPro.ms_usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")

public class UsuarioController {
    private final UsuarioService usuarioService;
    //Lista de usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioService.obtenerUsuarios());
    }
    //obtener usuarios por {id}
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuario(id));
    }
    //creacion de usuarios
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Usuario usuarioCreado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }
    //Eliminacion de usuarios por {id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
    //Validacion de cliente
    @GetMapping("/{id}/validar-cliente")
    public ResponseEntity<Boolean> esCliente(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            boolean esValido = usuario.getRol() == RolUsuario.CLIENTE;
            return ResponseEntity.ok(esValido);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
    //Validacion de Operador
    @GetMapping("/{id}/validar-operador")
    public ResponseEntity<Boolean> esOperadorServicio(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            boolean esValido = usuario.getRol() == RolUsuario.OPERADOR_SERVICIO;
            return ResponseEntity.ok(esValido);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}