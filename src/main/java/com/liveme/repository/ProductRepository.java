package com.liveme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liveme.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);
}