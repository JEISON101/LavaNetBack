package com.lavanet.lavanet_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lavanet.lavanet_api.models.Alquiler;
import com.lavanet.lavanet_api.models.Usuario;

import java.util.List;

public interface AlquilerRepository extends JpaRepository<Alquiler, Integer>{
  List<Alquiler> findByCliente(Usuario cliente);
  //List<Alquiler> findByProveedorId(@Param("idProveedor") Integer idProveedor);
  
}
