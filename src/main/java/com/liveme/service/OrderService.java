package com.liveme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liveme.entity.Order;
import com.liveme.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String createOrder(Order order) {
        orderRepository.save(order);
        return "Order created successfully!";
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
