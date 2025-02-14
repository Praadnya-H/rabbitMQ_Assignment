package com.cars24.rabbitmq.dao.impl;

import com.cars24.rabbitmq.dao.ProductDao;
import com.cars24.rabbitmq.data.entities.Product;
import com.cars24.rabbitmq.repo.ProductRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public boolean validateProduct(String productName, Integer quantity, Double price) {
        Optional<Product> productOpt = productRepo.findByName(productName);

        if (productOpt.isEmpty()) {
            log.error("Product not found: {}", productName);
            return false;
        }

        Product product = productOpt.get();

        if (!product.getPrice().equals(price)) {
            log.error("Price mismatch for product {}. Expected: {}, Received: {}",
                    productName, product.getPrice(), price);
            return false;
        }

        if (product.getAvailableQuantity() < quantity) {
            log.error("Insufficient quantity for product {}. Available: {}, Requested: {}",
                    productName, product.getAvailableQuantity(), quantity);
            return false;
        }

        return true;
    }

    @Override
    @Transactional
    public void updateProductQuantity(String productName, Integer quantity) {
        Product product = productRepo.findByName(productName)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productName));

        product.setAvailableQuantity(product.getAvailableQuantity() - quantity);
        productRepo.save(product);
    }
}
