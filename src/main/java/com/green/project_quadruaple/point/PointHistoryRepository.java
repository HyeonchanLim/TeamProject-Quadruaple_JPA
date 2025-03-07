package com.green.project_quadruaple.point;

import com.green.project_quadruaple.entity.model.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    @Query("select ph.remainPoint from PointHistory ph where ph.user.userId=:userId order by ph.pointHistoryId desc limit 1")
    Integer findRemainPointByUserId(Long userId);
}
