package com.lavanet.lavanet_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lavanet.lavanet_api.models.Lavadora;

public interface LavadoraRepository extends JpaRepository<Lavadora, Integer>{
  //List<Lavadora> findByProveedorId(Integer usuarioId);
}
