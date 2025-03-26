package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.Amenipoint;
import com.green.project_quadruaple.entity.model.AmenipointId;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AmenipointRepository extends JpaRepository<Amenipoint , AmenipointId> {
    void deleteByStayTourRestaurFest(StayTourRestaurFest strf);

    @Modifying
    @Transactional
    @Query("DELETE FROM Amenipoint a WHERE a.stayTourRestaurFest.strfId = :strfId")
    int deleteByStrfId(@Param("strfId") Long strfId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Amenipoint a WHERE a.id.amenityId IN :amenityIds AND a.id.strfId = :strfId")
    int deleteByAmenityIdAndStrfId(@Param("amenityIds") List<Long> amenityIds, @Param("strfId") Long strfId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Amenipoint a WHERE a.stayTourRestaurFest.strfId = :strfId ")
    int deleteByStrfId (@Param("strf_id") long strfId);
}
