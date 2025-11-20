package com.lavanet.lavanet_api.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lavanet.lavanet_api.config.JwtUtil;
import com.lavanet.lavanet_api.dtos.LoginRequest;
import com.lavanet.lavanet_api.dtos.RegisterRequest;
import com.lavanet.lavanet_api.dtos.ResponseDto;
import com.lavanet.lavanet_api.models.Usuario;
import com.lavanet.lavanet_api.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ArrayList<Usuario> getAllUsuarios() {
        return (ArrayList<Usuario>) usuarioRepository.findAll();
    }

    public Usuario getUsuarioId(Integer idUsuario) {
        Optional<Usuario> res = usuarioRepository.findById(idUsuario);
        return res.orElse(null);
    }

    public Usuario putUsuario(Usuario usuario, Integer idUsuario) {
        Usuario existente = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        existente.setNombres(usuario.getNombres());
        existente.setApellidos(usuario.getApellidos());
        existente.setFechaNacimiento(usuario.getFechaNacimiento());
        existente.setDireccion(usuario.getDireccion());
        existente.setCorreo(usuario.getCorreo());
        existente.setTelefono(usuario.getTelefono());

        return usuarioRepository.save(existente);
    }

    public void deleteUsuario(Integer idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    public Usuario register(RegisterRequest request) {
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            throw new RuntimeException("Este correo ya se encuentra registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setFechaNacimiento(request.getFechaNacimiento());
        usuario.setDireccion(request.getDireccion());
        usuario.setCorreo(request.getCorreo());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(request.getRol());
        usuario.setTelefono(request.getTelefono());
        
        usuarioRepository.save(usuario);
        
        return usuario;
    }

    public ResponseDto login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Correo no encontrado."));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol());
        return new ResponseDto(true, "Inicio de sesión exitoso", null, null, token);
    }
}