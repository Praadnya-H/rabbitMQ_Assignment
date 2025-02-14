package com.cars24.rabbitmq.controller;

import com.cars24.rabbitmq.data.dto.OrderDTO;
import com.cars24.rabbitmq.service.OrderProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Controller", description = "Order management APIs")
@Validated
public class OrderController {

    @Autowired
    private OrderProducerService orderProducerService;

    @PostMapping
    @Operation(summary = "Create a new order", description = "Creates a new order and sends it to processing queue")
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        try {
            orderProducerService.sendOrder(orderDTO);
            return ResponseEntity.ok("Order submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing order: " + e.getMessage());
        }
    }
}
