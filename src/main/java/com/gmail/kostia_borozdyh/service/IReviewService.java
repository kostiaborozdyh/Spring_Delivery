package com.gmail.kostia_borozdyh.service;

import com.gmail.kostia_borozdyh.entity.Review;

import java.util.List;

public interface IReviewService {
    List<Review> getAllReviews();

    void addReview(Review review);
}
