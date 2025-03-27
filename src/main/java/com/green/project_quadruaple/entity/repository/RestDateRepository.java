package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.RestDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestDateRepository extends JpaRepository<RestDate,Integer> {
//    void deleteByStrfId(Long strfId);


    @Query("SELECT a FROM RestDate a WHERE a.strfId.strfId = :strfId")
    List<RestDate> findByStrfId (Long strfId);

    @Modifying
    @Query("DELETE FROM RestDate a WHERE a.strfId.strfId = :strfId")
    int deleteByStrfId (Long strfId);

    @Modifying
    @Query("DELETE FROM RestDate a WHERE a.id.strfId = :strfId AND a.id.dayWeek = :restDate")
    int deleteRestDate(Long strfId , String restDate);


}
