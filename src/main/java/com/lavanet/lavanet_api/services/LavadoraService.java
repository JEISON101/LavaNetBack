package com.lavanet.lavanet_api.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lavanet.lavanet_api.models.Lavadora;
import com.lavanet.lavanet_api.repositories.LavadoraRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LavadoraService {

  @Autowired
  LavadoraRepository lavadoraRepository;

  public ArrayList<Lavadora> getAllLavadoras(){
    return (ArrayList<Lavadora>) lavadoraRepository.findAll();
  }

  public Lavadora getLavadoraId(Integer idLavadora){
    Optional<Lavadora> res = lavadoraRepository.findById(idLavadora);
    if(res.isPresent()) return res.get();
    return null;
  }
  
  @Transactional
  public Lavadora createLavadora(Lavadora lavadora){

    if (lavadora.getFotos() != null && !lavadora.getFotos().isEmpty()) {
      lavadora.getFotos().forEach(foto -> {
        foto.setLavadora(lavadora);
      });
    }

    Lavadora guardada = lavadoraRepository.save(lavadora);
    return guardada;
  }

  @Transactional
  public Lavadora updateLavadora(Integer idLavadora, Lavadora lavadoraActualizada) {
    Optional<Lavadora> lavadoraExistente = lavadoraRepository.findById(idLavadora);
    
    Lavadora lavadora = lavadoraExistente.get();
   
    lavadora.setMarca(lavadoraActualizada.getMarca());
    lavadora.setModelo(lavadoraActualizada.getModelo());
    lavadora.setDescripcion(lavadoraActualizada.getDescripcion());
    lavadora.setCapacidad(lavadoraActualizada.getCapacidad());
    lavadora.setPrecioHora(lavadoraActualizada.getPrecioHora());
    lavadora.setEstado(lavadoraActualizada.getEstado());
    
    // Actualizar fotos si se enviaron
    if (lavadoraActualizada.getFotos() != null) {
      lavadora.getFotos().clear();
      
      lavadoraActualizada.getFotos().forEach(foto -> {
        foto.setLavadora(lavadora);
        lavadora.getFotos().add(foto);
      });
    }
    
    return lavadoraRepository.save(lavadora);
  }

  @Transactional
  public void deleteLavadora(Integer idLavadora){
    lavadoraRepository.deleteById(idLavadora);
  }
}