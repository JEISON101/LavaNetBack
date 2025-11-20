package com.lavanet.lavanet_api.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavanet.lavanet_api.models.Alquiler;
import com.lavanet.lavanet_api.services.AlquilerService;


@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {
  @Autowired
  AlquilerService alquilerService;

  @GetMapping("/listar")
  public ArrayList<Alquiler> getAllAquileres() {
    try {
      return alquilerService.getAllAlquileres();
    } catch (Exception e) {
      return null;
    }
  }

  @GetMapping("/listar/cliente/{idUsuario}")
  public ArrayList<Alquiler> getAlquileresByCliente(@PathVariable Integer idUsuario) {
    try {
      return alquilerService.getAlquileresByCliente(idUsuario);
    } catch (Exception e) {
      return null;
    }
  }

  @GetMapping("/listar/proveedor/{idUsuario}")
  public ArrayList<Alquiler> getAlquileresByProveedor(@PathVariable Integer idUsuario) {
    try {
      return alquilerService.getAlquileresByProveedor(idUsuario);
    } catch (Exception e) {
      return null;
    }
  }

  @RequestMapping("/registrar")
  public Alquiler createAlquiler(@RequestBody Alquiler alquiler) {
      try {
        return alquilerService.createAlquiler(alquiler);
      } catch (Exception e) {
        return null;
      }
  }
  
  
  
}
