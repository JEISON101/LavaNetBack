package com.lavanet.lavanet_api.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.lavanet.lavanet_api.models.Favoritos;
import com.lavanet.lavanet_api.models.Usuario;
import com.lavanet.lavanet_api.repositories.FavoritosRepository;

@Service
public class FavoritosSevice {
  @Autowired
  FavoritosRepository favoritosRepository;

   public ArrayList<Favoritos> getFavoritosCliente(Usuario usuario) {
    return (ArrayList<Favoritos>) favoritosRepository.findAll();
  }

  public Favoritos createFavorito(@RequestBody Favoritos favorito){
    return favoritosRepository.save(favorito);
  }

  public void deleteFavorito(Integer idFavorito){
    favoritosRepository.deleteById(idFavorito);
  }

}
