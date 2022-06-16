package com.gmail.kostia_borozdyh.service.impl;

import com.gmail.kostia_borozdyh.entity.Review;
import com.gmail.kostia_borozdyh.repository.ReviewRepository;
import com.gmail.kostia_borozdyh.service.IReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReviewService implements IReviewService {
    private ReviewRepository reviewRepository;

    @Autowired
    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews() {
        log.info("get all reviews from dataBase");
        return reviewRepository.findAll();
    }

    @Override
    public void addReview(Review review) {
        reviewRepository.save(review);
        log.info("save review to dataBase");
    }
}
