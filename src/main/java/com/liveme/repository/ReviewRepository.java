package com.liveme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liveme.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

}