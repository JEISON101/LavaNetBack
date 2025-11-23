package com.lavanet.lavanet_api.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavanet.lavanet_api.dtos.ResponseDto;
import com.lavanet.lavanet_api.models.Usuario;
import com.lavanet.lavanet_api.services.UsuarioService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/listar")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<ResponseDto> getAllUsuarios(){
        try {
            ArrayList<Usuario> usuarios = usuarioService.getAllUsuarios();
            ResponseDto response = new ResponseDto(true, "Usuarios obtenidos correctamente", usuarios);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, "Error al obtener los usuarios", null, e.getMessage()));
        }
    }

    @GetMapping("/listar/{idUsuario}")
    public ResponseEntity<ResponseDto> getUsuariosId(@PathVariable Integer idUsuario) {
        try {
            Usuario usuario = usuarioService.getUsuarioId(idUsuario);
            ResponseDto response = new ResponseDto(true, "Usuario obtenido correctamente", usuario, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, "Error al obtener el usuario", null, e.getMessage()));
        }
    }

    @PutMapping("/editar/{idUsuario}")
    public ResponseEntity<ResponseDto> putUsuario(@PathVariable Integer idUsuario, @RequestBody Usuario usuario) {
        try {
            Usuario actualizado = usuarioService.putUsuario(usuario, idUsuario);
            ResponseDto response = new ResponseDto(true, "Usuario actualizado correctamente", actualizado, null);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, "Error al actualizar el usuario", null, e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar/{idUsuario}")
    public ResponseEntity<ResponseDto> deleteUsuario(@PathVariable Integer idUsuario){
        try {
            usuarioService.deleteUsuario(idUsuario);
            ResponseDto response = new ResponseDto(true, "Usuario eliminado correctamente", null, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, "Error al eliminar el usuario", null, e.getMessage()));
        }
    }

    // endpoints faltantes
    // => cambiar contraseña
    // => recuperar contraseña

    // ========== fin de la clase =========
}
