package com.liveme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.liveme.entity.Review;
import com.liveme.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public String createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @GetMapping("/all")
    public List<Review> getAllReviews() {
        return reviewService.getAllReview();
    }
}