package com.lavanet.lavanet_api.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.lavanet.lavanet_api.models.Alquiler;
import com.lavanet.lavanet_api.models.Usuario;
import com.lavanet.lavanet_api.repositories.AlquilerRepository;

@Service
public class AlquilerService {
  @Autowired
  AlquilerRepository alquilerRepository;
  @Autowired
  UsuarioService usuarioService;

  public ArrayList<Alquiler> getAllAlquileres() {
    return (ArrayList<Alquiler>) alquilerRepository.findAll();
  }

  public Alquiler getAlquilerById(Integer idAlquiler){
    Optional<Alquiler> res = alquilerRepository.findById(idAlquiler);
    if(res.isPresent()) return res.get();
    return null;
  }

  public ArrayList<Alquiler> getAlquileresByCliente(Integer idCliente){
    Usuario cliente = usuarioService.getUsuarioId(idCliente);
    if(cliente != null){
      return (ArrayList<Alquiler>) alquilerRepository.findByCliente(cliente);
    }
    return null;
  }
  
  public ArrayList<Alquiler> getAlquileresByProveedor(Integer idProveedor) {
      Usuario proveedor = usuarioService.getUsuarioId(idProveedor);
    if(proveedor != null){
      return (ArrayList<Alquiler>) alquilerRepository.findByCliente(proveedor);
    }
    return null;
  }

  public Alquiler createAlquiler(@RequestBody Alquiler alquiler){
    return alquilerRepository.save(alquiler);
  }

  public void deleteAlquiler(Integer idAlquiler){
    alquilerRepository.deleteById(idAlquiler);
  }
}
