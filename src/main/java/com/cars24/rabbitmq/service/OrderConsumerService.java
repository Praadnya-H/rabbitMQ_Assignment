package com.cars24.rabbitmq.service;

import com.cars24.rabbitmq.dao.impl.OrderDaoImpl;
import com.cars24.rabbitmq.dao.impl.ProductDaoImpl;
import com.cars24.rabbitmq.data.dto.OrderDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumerService {

    @Autowired
    private OrderDaoImpl orderDao;

    @Autowired
    private ProductDaoImpl productDao;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeOrder(OrderDTO orderDTO){
        log.info("Order received from RabbitMQ -> {}", orderDTO);
        if (productDao.validateProduct(
                orderDTO.getProductName(),
                orderDTO.getQuantity(),
                orderDTO.getPrice())) {
            try {
                processOrder(orderDTO);
                log.info("[consumeOrder] IN OrderConsumerService: Order processed and saved successfully");
            } catch (Exception e) {
                log.error("Error processing order: {}", e.getMessage());
            }
        } else {
            log.error("Order validation failed for product: {}", orderDTO.getProductName());
        }
    }

    @Transactional
    private void processOrder(OrderDTO orderDTO) {
        // Update product quantity
        productDao.updateProductQuantity(
                orderDTO.getProductName(),
                orderDTO.getQuantity()
        );
        orderDao.insertOrder(orderDTO);
    }
}
