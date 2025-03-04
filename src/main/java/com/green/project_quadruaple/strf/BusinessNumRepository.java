package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.entity.model.BusinessNum;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BusinessNumRepository extends JpaRepository<BusinessNum , String> {
    @Query("SELECT a.busiNum FROM BusinessNum a WHERE a.busiNum = :BusiNum")
    BusinessNum findByBusiNum(@Param("BusiNum") String BusiNum);
}
