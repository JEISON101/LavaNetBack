package com.lavanet.lavanet_api.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

    // Llave secreta usada para firmar y validar los tokens.
    @Value("${jwt.secret}")
    private String secret;

    // Tiempo de expiración configurado para el token en milisegundos.
    @Value("${jwt.expiration-ms}")
    private Long expirationMs;

    // Genera un token JWT asociado al correo del usuario y su rol, incluyendo
    // fecha de emisión y expiración.
    public String generateToken(String correo, String rol) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);
        
        return Jwts.builder()
                .setSubject(correo)
                .claim("rol", rol) // Agregar rol.
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret) // Firma usando algoritmo HS256.
                .compact();
    }

    // Obtiene el correo electrónico (subject) contenido en el token.
    public String getCorreo(String token) {
        return getClaims(token).getSubject();
    }

    // Obtiene el rol del usuario desde los claims personalizados.
    public String getRol(String token) {
        return (String) getClaims(token).get("rol");
    }

    // Extrae el conjunto de claims del token ya validado.
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Valida la estructura y la firma del token.
    // Retorna true si es válido, false en caso de firma inválida o error.
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    // Verifica si un token se encuentra expirado.
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getClaims(token).getExpiration();
            boolean expired = expiration.before(new Date());
            return expired;
        } catch (Exception e) {
            return true; // Ante cualquier error se asume expirado.
        }
    }
}