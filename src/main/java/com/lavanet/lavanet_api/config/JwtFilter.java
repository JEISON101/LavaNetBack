package com.lavanet.lavanet_api.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lavanet.lavanet_api.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestPath = request.getRequestURI();
        String header = request.getHeader("Authorization");

        log.info("🔍 Procesando request a: {}", requestPath);
        log.debug("📋 Authorization header: {}", header);

        // Si no hay header de autorización, continuar (Spring Security manejará el acceso)
        if (header == null || !header.startsWith("Bearer ")) {
            log.warn("⚠️ No se encontró token Bearer en el request a: {}", requestPath);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(7).trim(); // Eliminar "Bearer " y espacios
            log.debug("🔑 Token extraído: {}...", token.substring(0, Math.min(20, token.length())));

            // Validar el token
            if (!jwtUtil.validateToken(token)) {
                log.error("❌ Token inválido o expirado para ruta: {}", requestPath);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Token inválido o expirado\"}");
                return;
            }

            // Extraer información del token
            String correo = jwtUtil.getCorreo(token);
            log.info("📧 Correo extraído del token: {}", correo);

            // Cargar detalles del usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(correo);
            log.info("👤 Usuario cargado: {} con roles: {}", correo, userDetails.getAuthorities());

            // Establecer autenticación en el contexto de seguridad
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);
            log.info("✅ Autenticación exitosa para: {} en ruta: {}", correo, requestPath);

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.error("⏰ Token expirado: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Token expirado\",\"message\":\"" + e.getMessage() + "\"}");
            return;

        } catch (io.jsonwebtoken.MalformedJwtException e) {
            log.error("🔴 Token malformado: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Token malformado\",\"message\":\"" + e.getMessage() + "\"}");
            return;

        } catch (Exception e) {
            log.error("💥 Error inesperado al procesar token: {}", e.getMessage(), e);
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Error de autenticación\",\"message\":\"" + e.getMessage() + "\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}