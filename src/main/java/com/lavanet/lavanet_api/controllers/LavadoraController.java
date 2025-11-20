package com.lavanet.lavanet_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavanet.lavanet_api.models.Lavadora;
import com.lavanet.lavanet_api.services.LavadoraService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<ArrayList<Lavadora>> getAllLavadoras(Authentication authentication) {
        ArrayList<Lavadora> lavadoras = lavadoraService.getAllLavadoras();
        return ResponseEntity.ok(lavadoras);
    }

    @GetMapping("/listar/{idLavadora}")
    public ResponseEntity<Lavadora> getLavadoraById(@PathVariable Integer idLavadora, Authentication authentication) {
        Lavadora lavadora = lavadoraService.getLavadoraId(idLavadora);
    
        if (lavadora == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(lavadora);
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('Administrador'||'Proveedor')")
    public ResponseEntity<?> createLavadora(@RequestBody Lavadora lavadora, Authentication authentication) {
        try {
            Lavadora nuevaLavadora = lavadoraService.createLavadora(lavadora);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaLavadora);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\":\"Error al registrar lavadora\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/eliminar/{idLavadora}")
    @PreAuthorize("hasRole('Administrador'||'Proveedor')")
    public ResponseEntity<String> deleteLavadora(@PathVariable Integer idLavadora, Authentication authentication) {
        try {
            lavadoraService.deleteLavadora(idLavadora);
            log.info("✅ Lavadora eliminada exitosamente: {}", idLavadora);
            return ResponseEntity.ok("Lavadora eliminada exitosamente");
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ha ocurrido un error al eliminar la lavadora: " + e.getMessage());
        }
    }
}