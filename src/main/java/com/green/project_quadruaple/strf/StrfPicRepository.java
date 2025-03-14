package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import com.green.project_quadruaple.entity.model.StrfPic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StrfPicRepository extends JpaRepository<StrfPic, Long> {
    void deleteAllByStrfId(StayTourRestaurFest strf);
//    void deleteByStrfId(Long strfId);
}

