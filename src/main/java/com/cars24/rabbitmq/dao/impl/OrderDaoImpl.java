package com.cars24.rabbitmq.dao.impl;

import com.cars24.rabbitmq.dao.OrderDao;
import com.cars24.rabbitmq.data.dto.OrderDTO;
import com.cars24.rabbitmq.data.entities.Order;
import com.cars24.rabbitmq.repo.OrderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private OrderRepo orderRepository;

    @Override
    public void insertOrder(OrderDTO orderDTO) {
        log.info("[insertOrder] IN DAO: Order processed and saved successfully");
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setProductName(orderDTO.getProductName());
        order.setQuantity(orderDTO.getQuantity());
        order.setPrice(orderDTO.getPrice());
        order.setStatus("PROCESSED");
        order.setCreatedAt(LocalDateTime.now());

        orderRepository.save(order);
    };

}
