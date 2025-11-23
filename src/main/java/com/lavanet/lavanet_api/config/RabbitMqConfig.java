package com.lavanet.lavanet_api.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMqConfig {

  public static final String EXCHANGE_NAME = "lavanet_exchange";
  public static final String ROUTING_KEY_ALQUILER = "Alquiler routing key";
  public static final String QUEUE_ALQUILER = "Alquiler queue";

  @Bean
  public Queue queueAlquiler() {
    return new Queue(QUEUE_ALQUILER, true);
  }

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  @Bean
  public Binding bindingAlquiler(Queue queueAlquiler, TopicExchange topicExchange) {
    return BindingBuilder
        .bind(queueAlquiler)
        .to(topicExchange)
        .with(ROUTING_KEY_ALQUILER);
  }

  // beans para la conversion de mensajes a JSON
  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
  
  // bean para el template de AMQP
  @Bean
  public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
    final var template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(messageConverter());
    return template;
  }

}
