package com.green.project_quadruaple.notice;

import com.green.project_quadruaple.notice.model.res.NoticeLine;
import com.green.project_quadruaple.notice.model.res.NoticeLineRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<NoticeLine> checkNotice(Long userId, int startIdx, int size);

    NoticeLineRes countNotice(Long userId);
}
