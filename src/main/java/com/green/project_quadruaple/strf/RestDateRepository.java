package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.entity.model.RestDate;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RestDateRepository extends JpaRepository<RestDate,Integer> {
//    void deleteByStrfId(Long strfId);


    @Query("SELECT a FROM RestDate a WHERE a.strfId.strfId = :strfId")
    Optional<RestDate> findByStrfId (Long strfId);

    @Query("SELECT a FROM RestDate a WHERE a.strfId.strfId = :strfId")
    RestDate delByStrfId (Long strfId);


}
