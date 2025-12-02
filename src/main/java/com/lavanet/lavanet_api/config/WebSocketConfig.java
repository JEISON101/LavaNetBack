package com.lavanet.lavanet_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint al que se conectarán los clientes (React, móvil, etc.)
        registry.addEndpoint("/ws-ubicaciones")
                .setAllowedOriginPatterns("*") // puedes cambiar "*" por tu dominio
                .withSockJS(); // fallback para navegadores sin soporte WebSocket
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Prefijo para los mensajes enviados desde el cliente hacia el servidor
        registry.setApplicationDestinationPrefixes("/app");

        // Prefijo para los mensajes que el servidor emite hacia los clientes
        registry.enableSimpleBroker("/topic");

        // Opcional: si usas un broker externo (RabbitMQ, etc.), lo reemplazas aquí.
    }
}
