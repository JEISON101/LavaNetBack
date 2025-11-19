package com.lavanet.lavanet_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lavanet.lavanet_api.models.Ubicacion;
import com.lavanet.lavanet_api.models.Usuario;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {
  Optional<Ubicacion> findByUsuario(Usuario usuario);
}
