package com.lavanet.lavanet_api.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lavanet.lavanet_api.models.Lavadora;
import com.lavanet.lavanet_api.repositories.LavadoraRepository;

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

  public Lavadora createLavadora(Lavadora lavadora){
    return lavadoraRepository.save(lavadora);
  }

  public void deleteLavadora(Integer idLavadora){
    lavadoraRepository.deleteById(idLavadora);
  }
}
