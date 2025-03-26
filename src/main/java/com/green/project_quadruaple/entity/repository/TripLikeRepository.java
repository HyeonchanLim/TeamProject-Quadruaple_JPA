package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.TripLike;
import com.green.project_quadruaple.entity.model.TripLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripLikeRepository extends JpaRepository<TripLike, TripLikeId> {
    int deleteByTripReviewId_TripReviewId(Long tripReviewId);
    int countByTripReviewId_TripReviewId(Long tripReviewId);
}
