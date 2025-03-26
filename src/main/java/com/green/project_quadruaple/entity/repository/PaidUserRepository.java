package com.green.project_quadruaple.entity.repository;

import com.green.project_quadruaple.entity.model.PaidUser;
import com.green.project_quadruaple.entity.model.PaidUserIds;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PaidUserRepository extends JpaRepository<PaidUser, PaidUserIds> {
    @Modifying
    @Query(value = "DELETE FROM paid_user WHERE de_id = :deId", nativeQuery = true)
    void deleteByDeId(@Param("deId") Long deId);
}
