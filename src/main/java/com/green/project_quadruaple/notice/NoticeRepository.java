package com.green.project_quadruaple.notice;

import com.green.project_quadruaple.entity.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
