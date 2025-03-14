package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.entity.model.Amenipoint;
import com.green.project_quadruaple.entity.model.AmenipointId;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AmenipointRepository extends JpaRepository<Amenipoint , AmenipointId> {
    void deleteByStayTourRestaurFest(StayTourRestaurFest strf);

    @Query("SELECT a.amenity.title FROM Amenipoint a WHERE a.id = :amenityId")
    String findTitleByAmenityId(@Param("amenityId") Long amenityId);
}
