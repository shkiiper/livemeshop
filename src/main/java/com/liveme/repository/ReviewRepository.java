package com.liveme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liveme.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

}