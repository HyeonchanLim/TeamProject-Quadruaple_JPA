package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.LocationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationDetailRepository extends JpaRepository<LocationDetail , Long> {
}
