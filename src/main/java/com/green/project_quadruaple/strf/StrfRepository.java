package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import com.green.project_quadruaple.entity.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StrfRepository extends JpaRepository<StayTourRestaurFest, Long> {

    @Query("SELECT s.busiNum.user from StayTourRestaurFest s where s.strfId = :strfId")
    User findBusiIdById(long strfId);

    @Query("SELECT a.category FROM StayTourRestaurFest a WHERE a.busiNum.busiNum = :busiNum")
    Optional<StayTourRestaurFest> findByCategory(String businessNum);

    @Query("SELECT c.category FROM StayTourRestaurFest c " +
            "JOIN c.busiNum b " +
            "JOIN b.user u " +
            "WHERE u.userId = :userId")
    List<String> findCategoryByUserId(Long userId);

    @Query("SELECT s FROM StayTourRestaurFest s " +
            "JOIN s.busiNum b " +
            "JOIN b.user u " +
            "WHERE u.userId = :userId")
    Optional<StayTourRestaurFest> findByUserId(Long userId);


}
