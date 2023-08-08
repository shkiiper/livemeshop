package com.liveme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liveme.entity.Inventory;
import com.liveme.entity.Order;
import com.liveme.exception.BadRequestException;
import com.liveme.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) throws BadRequestException {
        if (order == null) {
            throw new BadRequestException("Ошибка", "Invalid order data", "order");
        }

        Order createdOrder = orderRepository.save(order);
        return createdOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int id) throws BadRequestException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Ошибка", "Заказ с указанным ID не найден", "id"));
    }

    public Order updateOrder(int id, Order order) throws BadRequestException {
        if (!orderRepository.existsById(id)) {
            throw new BadRequestException("Ошибка", "Заказ с указанным ID не найден", "id");
        }

        order.setId(id);
        return orderRepository.save(order);
    }

    public void deleteOrder(int id) throws BadRequestException {
        if (!orderRepository.existsById(id)) {
            throw new BadRequestException("Ошибка", "Заказ с указанным ID не найден", "id");
        }

        orderRepository.deleteById(id);
    }
}