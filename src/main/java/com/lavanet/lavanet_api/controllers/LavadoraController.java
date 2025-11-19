package com.lavanet.lavanet_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavanet.lavanet_api.models.Lavadora;
import com.lavanet.lavanet_api.services.LavadoraService;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/lavadoras")
public class LavadoraController {
  @Autowired
  LavadoraService lavadoraService;

  @GetMapping("/listar")
  public ArrayList<Lavadora> getAllLavadoras() {
      return lavadoraService.getAllLavadoras();
  }

  @GetMapping("/listar/{idLavadora}")
  public Lavadora getLavadoraById(@PathVariable Integer idLavadora) {
      return lavadoraService.getLavadoraId(idLavadora);
  }
  
  @PostMapping("/registrar")
  public Lavadora createLavadora(@RequestBody Lavadora lavadora) {
      return lavadoraService.createLavadora(lavadora);
  }
  
  @DeleteMapping("/eliminar/{idLavadora}")
  public String deleteLavadora(@PathVariable Integer idLavadora){
    try {
      lavadoraService.deleteLavadora(idLavadora);
      return "Lavadora eliminada exitosamente";
    } catch (Exception e) {
      return "Ha ocurrido un error al eliminar la lavadora";
    }
  }
}
