package com.lavanet.lavanet_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.lavanet.lavanet_api.models.Ubicacion;
import com.lavanet.lavanet_api.services.UbicacionService;

@RestController
public class UbicacionController {

    @Autowired
    private UbicacionService ubicacionService;

    @MessageMapping("/ubicacion")
    @SendTo("/topic/ubicaciones")
    public Ubicacion enviarUbicacion(Ubicacion ubicacion) {
        Ubicacion actualizada = ubicacionService.guardarOActualizarUbicacion(
            ubicacion.getUsuario().getIdUsuario(),
            ubicacion.getLatitud(),
            ubicacion.getLongitud()
        );
        return actualizada;
    }
}
