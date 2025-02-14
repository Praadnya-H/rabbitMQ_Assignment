package com.cars24.rabbitmq.dao;

import com.cars24.rabbitmq.data.dto.OrderDTO;

public interface OrderDao {
    void insertOrder(OrderDTO orderDTO);
}
