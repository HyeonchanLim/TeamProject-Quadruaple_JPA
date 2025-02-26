package com.green.project_quadruaple.notice;

import com.green.project_quadruaple.entity.model.NoticeReceive;
import com.green.project_quadruaple.entity.model.NoticeReceiveId;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeReceiveRepository extends JpaRepository<NoticeReceive, NoticeReceiveId> {
    @Query("SELECT COUNT(nr) FROM NoticeReceive nr WHERE nr.open = false AND nr.disable = false AND nr.id.userId = :userId")
    long countUnreadNoticesByUserId(@Param("userId") Long userId);
}
