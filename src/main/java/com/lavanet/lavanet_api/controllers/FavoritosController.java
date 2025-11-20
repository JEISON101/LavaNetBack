package com.lavanet.lavanet_api.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ArrayList<Favoritos> getFavoritosCliente(@PathVariable Integer idUsuario){
    Usuario usuario = usuarioService.getUsuarioId(idUsuario);
    if(usuario == null) return null;
    return favoritosSevice.getFavoritosCliente(usuario);
  }

  @PostMapping("/registrar")
  @PreAuthorize("hasRole('Administrador'||'Cliente')")
  public Favoritos createFavorito(@RequestBody Favoritos favorito) {
      return favoritosSevice.createFavorito(favorito);
  }

  @DeleteMapping("/eliminar/{idFavorito}")
  @PreAuthorize("hasRole('Administrador'||'Cliente')")
  public String deleteFavorito(@PathVariable Integer idFavorito){
    try {
      favoritosSevice.deleteFavorito(idFavorito);
      return "Proveedor elimado de favoritos exitosamente";
    } catch (Exception e) {
      return "Ha ocurrido un error al eliminar el proveedor de favoritos";
    }
  }
  
}
