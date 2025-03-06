package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import com.green.project_quadruaple.entity.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StrfRepository extends JpaRepository<StayTourRestaurFest, Long> {

    @Query("SELECT s.busiNum.user from StayTourRestaurFest s where s.strfId = :strfId")
    User findBusiIdById(long strfId);

    @Query("SELECT a.category FROM StayTourRestaurFest a WHERE a.busiNum.busiNum = :busiNum")
    Optional<StayTourRestaurFest> findByCategory(String businessNum);
}
