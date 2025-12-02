package com.lavanet.lavanet_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavanet.lavanet_api.dtos.ResponseDto;
import com.lavanet.lavanet_api.models.Lavadora;
import com.lavanet.lavanet_api.services.LavadoraService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/lavadoras")
public class LavadoraController {
    
    @Autowired
    LavadoraService lavadoraService;

    @GetMapping("/listar")
    public ResponseEntity<ResponseDto> getAllLavadoras(Authentication authentication) {
        ArrayList<Lavadora> lavadoras = lavadoraService.getAllLavadoras();
        return ResponseEntity.ok(new ResponseDto(true, "Lista de lavadoras obtenida con exito", lavadoras));
    }

    @GetMapping("/listar/{idLavadora}")
    public ResponseEntity<ResponseDto> getLavadoraById(@PathVariable Integer idLavadora, Authentication authentication) {
        Lavadora lavadora = lavadoraService.getLavadoraId(idLavadora);
        if (lavadora == null) {return ResponseEntity.status(404).body(new ResponseDto(false, "Lavadora no encontrada", null));}  
        return ResponseEntity.badRequest().body(new ResponseDto(true, "Lavadora encontrada", lavadora));
    }

    @PostMapping("/registrar")
    public ResponseEntity<ResponseDto> createLavadora(@RequestBody Lavadora lavadora, Authentication authentication) {
        try {
            Lavadora nuevaLavadora = lavadoraService.createLavadora(lavadora);
            return ResponseEntity.status(201).body(new ResponseDto(true, "Lavadora registrada con exito", nuevaLavadora));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, "Error al registrar la lavadora: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/eliminar/{idLavadora}")
    public ResponseEntity<ResponseDto> deleteLavadora(@PathVariable Integer idLavadora, Authentication authentication) {
        try {
            lavadoraService.deleteLavadora(idLavadora);
            return ResponseEntity.ok(new ResponseDto(true, "Lavadora eliminada con exito", null));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, "Error al eliminar la lavadora: " + e.getMessage(), null));
        }
    }

    // => cambiar estado de lavadora

    //  =========== fin de la clase ===========
}