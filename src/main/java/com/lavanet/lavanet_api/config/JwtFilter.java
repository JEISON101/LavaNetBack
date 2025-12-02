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

    // encargada de validar, analizar y extraer información del token.
    private final JwtUtil jwtUtil;

    // carga los detalles del usuario.
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Permitir solicitudes OPTIONS (preflight) sin validación de token.
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        // Si el header no existe o no tiene el formato esperado, continuar la cadena sin autenticación.
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.substring(7).trim(); // Eliminar "Bearer " y espacios
            // Validar el token según las reglas establecidas en JwtUtil.
            if (!jwtUtil.validateToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Token inválido o expirado\"}");
                return;
            }

            // Evitar recargar autenticación si ya existe en el contexto.
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                // Obtener correo desde el token.
                String correo = jwtUtil.getCorreo(token);

                // Cargar detalles del usuario para configurar autenticación.
                UserDetails userDetails = userDetailsService.loadUserByUsername(correo);

                // Crear token de autenticación con rol del usuario.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Establecer autenticación en el contexto de seguridad.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Token expirado\",\"message\":\"" + e.getMessage() + "\"}");
            return;

        } catch (io.jsonwebtoken.MalformedJwtException e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Token malformado\",\"message\":\"" + e.getMessage() + "\"}");
            return;

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Error de autenticación\",\"message\":\"" + e.getMessage() + "\"}");
            return;
        }

        // Continuar con la cadena de filtros.
        filterChain.doFilter(request, response);
    }
}