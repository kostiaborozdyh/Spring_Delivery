package com.gmail.kostia_borozdyh.repository;

import com.gmail.kostia_borozdyh.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
