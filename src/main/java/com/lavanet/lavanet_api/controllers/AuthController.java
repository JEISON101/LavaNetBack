package com.lavanet.lavanet_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavanet.lavanet_api.dtos.LoginRequest;
import com.lavanet.lavanet_api.dtos.RegisterRequest;
import com.lavanet.lavanet_api.dtos.ResponseDto;
import com.lavanet.lavanet_api.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // Servicio encargado de la lógica de usuarios (registro, login, etc.)
    private final UsuarioService usuarioService;

    /**
     * Endpoint para registrar un nuevo usuario.
     * 
     * - Recibe un RegisterRequest con los datos del usuario.
     * - Delegamos la creación al servicio.
     * - Retornamos un ResponseDto estándar con el usuario creado.
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(new ResponseDto(true, "Usuario registrado correctamente", usuarioService.register(request)));
    }

    /**
     * Endpoint de login.
     *
     * - Recibe las credenciales (correo + contraseña).
     * - El servicio valida el usuario y genera el token.
     * - Se retorna un ResponseDto con el token incluido.
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(usuarioService.login(request));
    }
}
