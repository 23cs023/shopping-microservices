package com.shopcentral.backend.controller;

import com.shopcentral.backend.model.Order;
import com.shopcentral.backend.service.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    // ==============================
    // CREATE ORDER
    // ==============================

    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestBody Order order
    ) {

        try {

            Order savedOrder =
                    orderService.createOrder(order);

            return ResponseEntity.ok(savedOrder);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // ==============================
    // GET ALL ORDERS
    // ==============================

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }


    // ==============================
    // GET ORDER BY ID
    // ==============================

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable Long id
    ) {

        return orderService
                .getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity
                                .notFound()
                                .build()
                );
    }
}