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
import com.lavanet.lavanet_api.models.Favoritos;
import com.lavanet.lavanet_api.models.Usuario;
import com.lavanet.lavanet_api.services.FavoritosSevice;
import com.lavanet.lavanet_api.services.UsuarioService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/favoritos")
public class FavoritosController {
  @Autowired
  FavoritosSevice favoritosSevice;
  @Autowired
  UsuarioService usuarioService;

  @GetMapping("/listar/{idUsuario}")
  @PreAuthorize("hasRole('Administrador'||'Cliente')")
  public ResponseEntity<ResponseDto> getFavoritosCliente(@PathVariable Integer idUsuario){
    Usuario usuario = usuarioService.getUsuarioId(idUsuario);
    if(usuario == null) return ResponseEntity.badRequest().body(new ResponseDto(false, "El usuario no existe", new ArrayList<>()));
    ArrayList<Favoritos> favoritos = favoritosSevice.getFavoritosCliente(usuario);
    return ResponseEntity.ok().body(new ResponseDto(true, "Lista de favoritos obtenida exitosamente", favoritos));
  }

  @PostMapping("/registrar")
  @PreAuthorize("hasRole('Administrador'||'Cliente')")
  public ResponseEntity<ResponseDto> createFavorito(@RequestBody Favoritos favorito) {
    try {
      Favoritos newFavorito = favoritosSevice.createFavorito(favorito);
      return ResponseEntity.ok().body(new ResponseDto(true, "Favorito registrado exitosamente", newFavorito));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseDto(false, "Ha ocurrido un error al registrar el favorito", null));
    }
  }

  @DeleteMapping("/eliminar/{idFavorito}")
  @PreAuthorize("hasRole('Administrador'||'Cliente')")
  public ResponseEntity<ResponseDto> deleteFavorito(@PathVariable Integer idFavorito){
    try {
      favoritosSevice.deleteFavorito(idFavorito);
      return ResponseEntity.ok().body(new ResponseDto(true, "Proveedor eliminado de favoritos exitosamente", null));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseDto(false, "Ha ocurrido un error al eliminar el favorito", null));
    }
  }
  
}
