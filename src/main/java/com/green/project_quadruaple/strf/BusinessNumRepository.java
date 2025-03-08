package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.entity.model.BusinessNum;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface BusinessNumRepository extends JpaRepository<BusinessNum , String> {
    @Query("SELECT a.busiNum FROM BusinessNum a WHERE a.busiNum = :busiNum")
    BusinessNum findByBusiNum(@Param("busiNum") String busiNum);


    @Query("SELECT bn.busiNum FROM BusinessNum bn WHERE bn.user.userId = :userId")
    List<String> findBusiNumByUserId(@Param("userId") Long userId);

    @Query("SELECT bn.busiNum FROM BusinessNum bn " +
            "JOIN StayTourRestaurFest s ON bn.busiNum = s.busiNum.busiNum " +
            "WHERE s.strfId = :strfId")
    Optional<String> findBusiNumByStrfId(@Param("strfId") Long strfId);


    @Query("SELECT a.busiNum FROM BusinessNum a WHERE a.user.userId = :userId")
    String findBusinessNumByUserId(@Param("userId") Long userId);

    @Query("SELECT a FROM BusinessNum a WHERE a.busiNum = :busiNum")
    BusinessNum findByBusinessNum(@Param("busiNum") String busiNum);

}
