package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.TripReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripReviewRepository extends JpaRepository<TripReview, Long> {
    int countByUser_UserId(Long userId);
    int countAllBy();
}
