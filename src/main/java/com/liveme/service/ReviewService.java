package com.liveme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liveme.entity.Review;
import com.liveme.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public String createReview(Review review) {
        reviewRepository.save(review);
        return "Review created successfully!";
    }

    public List<Review> getAllReview() {
        return reviewRepository.findAll();
    }

}
