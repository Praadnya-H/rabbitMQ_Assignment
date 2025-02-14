package com.cars24.rabbitmq.service;

import com.cars24.rabbitmq.data.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public void sendOrder(OrderDTO orderDTO) {
        log.info("Order sent to RabbitMQ -> {}", orderDTO);
        rabbitTemplate.convertAndSend(exchange, routingKey, orderDTO);
    }
}
