package com.green.project_quadruaple.notice;

import com.green.project_quadruaple.notice.model.dto.NoticeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<NoticeLine> checkNotice(Long userId);
}
