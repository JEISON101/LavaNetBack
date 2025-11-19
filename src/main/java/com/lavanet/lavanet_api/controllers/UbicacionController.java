package com.lavanet.lavanet_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.lavanet.lavanet_api.models.Ubicacion;
import com.lavanet.lavanet_api.services.UbicacionService;

@Controller
public class UbicacionController {

    @Autowired
    private UbicacionService ubicacionService;

    @MessageMapping("/ubicacion") // los clientes envían aquí
    @SendTo("/topic/ubicaciones") // y todos los suscritos reciben
    public Ubicacion enviarUbicacion(Ubicacion ubicacion) {
        // guarda o actualiza la ubicación antes de retransmitirla
        Ubicacion actualizada = ubicacionService.guardarOActualizarUbicacion(
            ubicacion.getUsuario().getIdUsuario(),
            ubicacion.getLatitud(),
            ubicacion.getLongitud()
        );
        return actualizada;
    }
}
