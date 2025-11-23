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

    /**
     * Obtiene todos los usuarios registrados.
     * Ideal para listados generales o administración.
     */
    public ArrayList<Usuario> getAllUsuarios() {
        return (ArrayList<Usuario>) usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID.
     * @param idUsuario identificador del usuario
     * @return el usuario encontrado o null si no existe
     */
    public Usuario getUsuarioId(Integer idUsuario) {
        Optional<Usuario> res = usuarioRepository.findById(idUsuario);
        return res.orElse(null);
    }

    /**
     * Actualiza la información de un usuario existente.
     * Mantiene el ID original y solo reemplaza los campos recibidos.
     */
    public Usuario putUsuario(Usuario usuario, Integer idUsuario) {
        Usuario existente = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualización de datos personales
        existente.setNombres(usuario.getNombres());
        existente.setApellidos(usuario.getApellidos());
        existente.setFechaNacimiento(usuario.getFechaNacimiento());
        existente.setDireccion(usuario.getDireccion());
        existente.setTelefono(usuario.getTelefono());

        return usuarioRepository.save(existente);
    }

    /**
     * Elimina un usuario por su ID.
     * No retorna nada; si el ID no existe, Spring lanza la excepción correspondiente.
     */
    public void deleteUsuario(Integer idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Incluye validación de correo duplicado y encriptación de contraseña.
     */
    public Usuario register(RegisterRequest request) {
        // Validación: evitar correos repetidos
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

    /**
     * Autentica al usuario comparando credenciales
     * y genera un token JWT si todo es válido.
     */
    public ResponseDto login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Correo no encontrado."));

        // Validación de contraseña
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        // Generación del JWT
        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol());
        return new ResponseDto(true, "Inicio de sesión exitoso", null, null, token);
    }
}