package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.entity.model.Recent;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StrfRepository extends JpaRepository<StayTourRestaurFest, Long> {

}
