package com.gmail.kostia_borozdyh.service;

import com.gmail.kostia_borozdyh.entity.Review;
import com.gmail.kostia_borozdyh.repository.ReviewRepository;
import com.gmail.kostia_borozdyh.service.impl.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;
    @MockBean
    private ReviewRepository reviewRepository;

    @Test
    public void addReviewTest() {
        Review review = new Review();
        reviewService.addReview(review);
        Mockito.verify(reviewRepository, Mockito.times(1)).save(review);
    }
}
