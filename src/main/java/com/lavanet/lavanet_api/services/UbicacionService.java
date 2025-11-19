package com.lavanet.lavanet_api.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lavanet.lavanet_api.models.Ubicacion;
import com.lavanet.lavanet_api.models.Usuario;
import com.lavanet.lavanet_api.repositories.UbicacionRepository;
import com.lavanet.lavanet_api.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UbicacionService {

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Ubicacion guardarOActualizarUbicacion(int usuarioId, double latitud, double longitud) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + usuarioId));

        Optional<Ubicacion> existente = ubicacionRepository.findByUsuario(usuario);
        Ubicacion ubicacion;

        if (existente.isPresent()) {
            ubicacion = existente.get();
            ubicacion.setLatitud(latitud);
            ubicacion.setLongitud(longitud);
            ubicacion.setActualizado(new Date());
        } else {
            ubicacion = new Ubicacion();
            ubicacion.setUsuario(usuario);
            ubicacion.setLatitud(latitud);
            ubicacion.setLongitud(longitud);
            ubicacion.setActualizado(new Date());
        }

        return ubicacionRepository.save(ubicacion);
    }

    public Optional<Ubicacion> obtenerUbicacionPorUsuario(int usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + usuarioId));
        return ubicacionRepository.findByUsuario(usuario);
    }
}
