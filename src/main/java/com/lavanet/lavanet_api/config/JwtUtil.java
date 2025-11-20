package com.lavanet.lavanet_api.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private Long expirationMs;

    public String generateToken(String correo, String rol) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);
        
        log.info("🔐 Generando token para: {} con rol: {}", correo, rol);
        log.debug("⏰ Token expirará en: {} ms ({} horas)", expirationMs, expirationMs / 3600000);
        
        return Jwts.builder()
                .setSubject(correo)
                .claim("rol", rol)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getCorreo(String token) {
        return getClaims(token).getSubject();
    }

    public String getRol(String token) {
        return (String) getClaims(token).get("rol");
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            log.debug("✅ Token válido");
            return true;
            
        } catch (SignatureException ex) {
            log.error("❌ Firma JWT inválida: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("❌ Token JWT malformado: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("❌ Token JWT expirado: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("❌ Token JWT no soportado: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("❌ Claims JWT vacío: {}", ex.getMessage());
        }
        
        return false;
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getClaims(token).getExpiration();
            boolean expired = expiration.before(new Date());
            log.debug("⏰ Token expirado: {} (expira: {})", expired, expiration);
            return expired;
        } catch (Exception e) {
            log.error("❌ Error al verificar expiración: {}", e.getMessage());
            return true;
        }
    }
}