package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.LocationDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocationDetailRepository extends JpaRepository<LocationDetail , Long> {
    @Query("SELECT a.locationDetailId , a.detailTitle , a.location.locationId FROM LocationDetail a WHERE a.detailTitle = :detailTitle ")
    Optional<LocationDetail> findByTitle(@Param("detail_title") String detailTitle);
}
