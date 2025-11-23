package com.lavanet.lavanet_api.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.lavanet.lavanet_api.config.RabbitMqConfig;
import com.lavanet.lavanet_api.dtos.MessageAlquiler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitListenerService {

  @RabbitListener(queues = RabbitMqConfig.QUEUE_ALQUILER)
  public MessageAlquiler listenAlquilerQueue(MessageAlquiler message) {
    return message;
  }

}

