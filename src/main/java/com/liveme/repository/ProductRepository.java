package com.liveme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liveme.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}