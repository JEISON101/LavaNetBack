package com.lavanet.lavanet_api.services;

import java.util.ArrayList;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lavanet.lavanet_api.models.Usuario;
import com.lavanet.lavanet_api.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        log.debug("🔍 Buscando usuario por correo: {}", correo);
        
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> {
                    log.error("❌ Usuario no encontrado: {}", correo);
                    return new UsernameNotFoundException("Usuario no encontrado: " + correo);
                });

        var authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
        
        log.info("✅ Usuario cargado: {} con rol: ROLE_{}", correo, usuario.getRol());

        return new User(
                usuario.getCorreo(),
                usuario.getPassword(),
                authorities
        );
    }
}