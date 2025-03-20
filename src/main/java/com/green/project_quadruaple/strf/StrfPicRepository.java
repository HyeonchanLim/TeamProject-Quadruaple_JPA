package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import com.green.project_quadruaple.entity.model.StrfPic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StrfPicRepository extends JpaRepository<StrfPic, Long> {
    void deleteAllByStrfId(StayTourRestaurFest strf);
//    void deleteByStrfId(Long strfId);

    @Query("SELECT a FROM StrfPic a WHERE a.picName = :picName ")
    Optional<StrfPic> findByPicName(String picName);

    @Modifying
    @Query("DELETE FROM StrfPic a WHERE a.picName = :picName ")
    int deleteByPicName (String picName);

    @Query("SELECT COUNT(a) FROM StrfPic a WHERE a.strfId = :strf")
    long countByStrfId(StayTourRestaurFest strf);
}

