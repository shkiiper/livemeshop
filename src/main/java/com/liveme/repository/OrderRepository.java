package com.liveme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liveme.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}