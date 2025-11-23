package com.lavanet.lavanet_api.controllers;

import java.util.ArrayList;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavanet.lavanet_api.config.RabbitMqConfig;
import com.lavanet.lavanet_api.dtos.ResponseDto;
import com.lavanet.lavanet_api.models.Alquiler;
import com.lavanet.lavanet_api.services.AlquilerService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/alquileres")
@RequiredArgsConstructor
public class AlquilerController {
  @Autowired
  AlquilerService alquilerService;

  private final RabbitTemplate rabbitTemplate;

  @GetMapping("/listar")
  public ResponseEntity<ResponseDto> getAllAquileres() {
    try {
      ArrayList<Alquiler> alquileres = alquilerService.getAllAlquileres();
      return ResponseEntity.ok(new ResponseDto(true, "Lista de alquileres obtenida con exito", alquileres));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseDto(false, "Error al obtener la lista de alquileres", null));
    }
  }

  @GetMapping("/listar/cliente/{idUsuario}")
  public ResponseEntity<ResponseDto> getAlquileresByCliente(@PathVariable Integer idUsuario) {
    try {
      ArrayList<Alquiler> alquileres = alquilerService.getAlquileresByCliente(idUsuario);
      return ResponseEntity.ok(new ResponseDto(true, "Lista de alquileres del cliente obtenida con exito", alquileres));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseDto(false, "Error al obtener la lista de alquileres del cliente", null));
    }
  }

  @GetMapping("/listar/proveedor/{idUsuario}")
  public ResponseEntity<ResponseDto> getAlquileresByProveedor(@PathVariable Integer idUsuario) {
    try {
      ArrayList<Alquiler> alquileres = alquilerService.getAlquileresByProveedor(idUsuario);
      return ResponseEntity.ok(new ResponseDto(true, "Lista de alquileres del proveedor obtenida con exito", alquileres));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new ResponseDto(false, "Error al obtener la lista de alquileres del proveedor", null));
    }
  }

  @RequestMapping("/registrar")
  public ResponseEntity<ResponseDto> createAlquiler(@RequestBody Alquiler alquiler) {
      try {
        Alquiler res = alquilerService.createAlquiler(alquiler);
        // se envia un messaje a RabbitMQ
        this.rabbitTemplate.convertAndSend(
          RabbitMqConfig.EXCHANGE_NAME,
          RabbitMqConfig.ROUTING_KEY_ALQUILER,
          res.getDireccionEntrega()
        );
        return ResponseEntity.ok(new ResponseDto(true, "Alquiler registrado con exito", res));
      } catch (Exception e) {
        return ResponseEntity.badRequest().body(new ResponseDto(false, "Error al registrar el alquiler", null));
      }
  }

  // => cambar estado del alquiler
  
  // =========== fin de la clase =========
}
