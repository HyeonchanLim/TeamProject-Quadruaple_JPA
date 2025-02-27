package com.green.project_quadruaple.tripreview.repository;

import com.green.project_quadruaple.entity.model.RecentTr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecentTrRepository extends JpaRepository<RecentTr, Long> {
    int deleteByTripReviewId_TripReviewId(Long tripReviewId);
}
