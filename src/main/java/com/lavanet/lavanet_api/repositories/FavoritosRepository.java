package com.lavanet.lavanet_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lavanet.lavanet_api.models.Favoritos;
import com.lavanet.lavanet_api.models.Usuario;

public interface FavoritosRepository extends JpaRepository<Favoritos, Integer>{
  List<Favoritos> findByCliente(Usuario usuario);
}
