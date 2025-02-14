package com.green.project_quadruaple.memo;

import com.green.project_quadruaple.memo.model.MemoDto;
import com.green.project_quadruaple.memo.model.Req.MemoPostReq;
import com.green.project_quadruaple.memo.model.Req.MemoUpReq;
import com.green.project_quadruaple.memo.model.Res.MemoGetRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface MemoMapper {

    MemoUpReq selUserIdByMemoId(Long memoId);

    MemoGetRes selectMemo(Long memoId);

    void postScheMemo(MemoPostReq scheMemoDto);

    void insMemo(MemoPostReq memoDto);

    Long findMemoOwnerId(Long memoId);

    void patchMemo(MemoUpReq memoDto);

    void deleteMemo(Long memoId);

    void deleteMemoTest(Long memoId);

    Long selTripUserId(Long tripId, Long userId);
}
