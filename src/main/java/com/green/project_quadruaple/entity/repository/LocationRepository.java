package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location , Long> {
    String findTitleById(Long id);
}
