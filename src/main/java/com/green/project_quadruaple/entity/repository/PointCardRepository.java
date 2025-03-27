package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.PointCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointCardRepository extends JpaRepository<PointCard, Long> {
    PointCard findByAvailable(int available);
}
