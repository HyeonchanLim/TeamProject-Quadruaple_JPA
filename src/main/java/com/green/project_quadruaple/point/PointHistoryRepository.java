package com.green.project_quadruaple.point;

import com.green.project_quadruaple.entity.model.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    @Query("select ph.remainPoint from PointHistory ph where ph.user.userId=:userId order by ph.pointHistoryId desc limit 1")
    Integer findRemainPointByUserId(Long userId);

    @Query("""
        select p.remainPoint from PointHistory p
        where p.user.userId = :signedUserId
        order by p.pointHistoryId desc
        """)
    List<Integer> findRemainPointByUserId(Long signedUserId, Pageable pageable);
}
