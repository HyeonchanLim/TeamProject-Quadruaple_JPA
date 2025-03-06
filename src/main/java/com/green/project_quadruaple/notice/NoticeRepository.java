package com.green.project_quadruaple.notice;

import com.green.project_quadruaple.entity.model.Notice;
import com.green.project_quadruaple.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query(value = "SELECT DISTINCT user_id FROM notice_event_log WHERE event_time > :lastCheckedTime", nativeQuery = true)
    List<Long> getUsersWithNewNotices(LocalDateTime lastCheckedTime);
    @Modifying
    @Query(value = "DELETE FROM notice_event_log WHERE event_type = 'NEW_NOTICE' AND user_id = :userId", nativeQuery = true)
    void clearProcessedNotifications(Long userId);

}
