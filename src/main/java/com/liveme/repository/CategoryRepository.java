package com.liveme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liveme.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}