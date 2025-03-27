package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.Scrap;
import com.green.project_quadruaple.entity.ids.ScrapId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, ScrapId> {
    int deleteByTripReviewId_TripReviewId(Long tripReviewId);
}
