package com.cars24.rabbitmq.repo;

import com.cars24.rabbitmq.data.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);
}
