package com.green.project_quadruaple.tripreview.repository;

import com.green.project_quadruaple.entity.model.Scrap;
import com.green.project_quadruaple.entity.model.ScrapId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, ScrapId> {
    int deleteByTripReviewId_TripReviewId(Long tripReviewId);
}
