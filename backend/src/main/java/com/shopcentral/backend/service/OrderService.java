package com.shopcentral.backend.service;

import com.shopcentral.backend.model.Order;
import com.shopcentral.backend.model.Product;
import com.shopcentral.backend.repository.OrderRepository;
import com.shopcentral.backend.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(Order order) {

        // Default status
        if (order.getStatus() == null ||
            order.getStatus().isBlank()) {

            order.setStatus("PLACED");
        }

        // Default created time
        if (order.getCreatedAt() == null) {

            order.setCreatedAt(LocalDateTime.now());
        }

        // Find product
        Optional<Product> productOptional =
                productRepository.findByName(
                        order.getProductName()
                );

        if (productOptional.isEmpty()) {

            throw new RuntimeException(
                    "Product not found: "
                    + order.getProductName()
            );
        }

        Product product = productOptional.get();

        // Check stock
        if (product.getStock() <= 0) {

            throw new RuntimeException(
                    "Product is out of stock: "
                    + product.getName()
            );
        }

        // Reduce stock
        product.setStock(
                product.getStock() - 1
        );

        // Save updated stock
        productRepository.save(product);

        // Save order
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {

        return orderRepository.findById(id);
    }
}