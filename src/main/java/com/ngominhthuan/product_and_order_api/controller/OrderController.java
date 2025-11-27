package com.ngominhthuan.product_and_order_api.controller;

import com.ngominhthuan.product_and_order_api.model.Order;
import com.ngominhthuan.product_and_order_api.DTO.OrderRequest;
import com.ngominhthuan.product_and_order_api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // POST /api/orders
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        try {
            Order newOrder = orderService.createOrder(request);
            return ResponseEntity.ok(newOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
