package com.ngominhthuan.product_and_order_api.service;

import com.ngominhthuan.product_and_order_api.DTO.OrderRequest;
import com.ngominhthuan.product_and_order_api.model.*;
import com.ngominhthuan.product_and_order_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    // Create a new order
    public Order createOrder(OrderRequest request) {
        // Find User
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create Order
        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");
        
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        // Iterate through each item the customer wants to buy
        for (OrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Out of stock! Product " + product.getName() + " only has " + product.getStock() + " left.");
            }

            // Deduct stock
            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);

            // Create OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);
            totalAmount += product.getPrice() * itemRequest.getQuantity();
        }

        // Save order
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }
}