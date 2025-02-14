package com.cars24.rabbitmq.dao;

public interface ProductDao {
    boolean validateProduct(String productName, Integer quantity, Double price);
    void updateProductQuantity(String productName, Integer quantity);
}
