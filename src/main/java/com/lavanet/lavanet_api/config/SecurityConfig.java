package com.lavanet.lavanet_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.lavanet.lavanet_api.services.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // Filtro personalizado encargado de validar y procesar el JWT en cada petición.
    private final JwtFilter jwtFilter;

    // Servicio que carga los detalles de usuario desde la base de datos para el proceso de autenticación.
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configura la cadena de filtros de seguridad. Incluye reglas de acceso,
        // deshabilita CSRF (útil en APIs stateless), habilita CORS y agrega el filtro JWT.
        return http
                .csrf(csrf -> csrf.disable()) // Desactiva protección CSRF dado que la API funciona sin sesiones.
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Aplica configuración CORS definida abajo.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Enfoque stateless: sin sesiones.
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos para autenticación.
                        .requestMatchers("/api/auth/**").permitAll()
                        // Permitir solicitudes OPTIONS (CORS preflight).
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        // El resto de solicitudes requiere autenticación.
                        .anyRequest().authenticated()
                )
                // Inserta el filtro JWT antes del filtro de autenticación por username/password.
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // Define el servicio que provee los datos de usuario para autenticación.
                .userDetailsService(userDetailsService)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // Configuración CORS que permite acceso desde cualquier origen y con cualquier método o header.
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); // Acepta peticiones desde cualquier origen.
        config.addAllowedHeader("*"); // Permite cualquier header.
        config.addAllowedMethod("*"); // Permite cualquier método HTTP.
        config.setAllowCredentials(true); // Permite envío de credenciales si aplica.
    
        // Registra esta configuración para que aplique a todos los endpoints.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Codificador de contraseñas basado en BCrypt, estándar recomendado para hashing seguro.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Obtiene el AuthenticationManager usado para el proceso de autenticación.
        return config.getAuthenticationManager();
    }
}
