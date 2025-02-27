package com.green.project_quadruaple.tripreview.repository;

import com.green.project_quadruaple.entity.model.TripReviewPic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripReviewPicRepository extends JpaRepository<TripReviewPic, Long> {
    int deleteByTripReview_TripReviewId(Long tripReviewId);
}
