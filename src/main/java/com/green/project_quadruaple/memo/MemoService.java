package com.green.project_quadruaple.memo;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.memo.model.Req.MemoPostReq;
import com.green.project_quadruaple.memo.model.Req.MemoUpReq;
import com.green.project_quadruaple.memo.model.Res.MemoGetRes;
import com.green.project_quadruaple.trip.TripMapper;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoMapper memoMapper;
    private final AuthenticationFacade authenticationFacade;
    private final TripMapper tripMapper;

    @Transactional
    public  ResponseEntity<ResponseWrapper<MemoGetRes>> getMemo(Long memoId) {
        long signedUserId=authenticationFacade.getSignedUserId();
        if(signedUserId==0){ return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));}
        MemoGetRes memo = memoMapper.selectMemo(memoId);

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(),memo));
    }


    @Transactional
    public ResponseWrapper<Long> addMemo(MemoPostReq memoDto) {
        long signedUserId = authenticationFacade.getSignedUserId();
        Long tripId = memoDto.getTripId();
        if(signedUserId==0){
            return new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), 0L);
        }
        Long tripUserId = memoMapper.selTripUserId(tripId, signedUserId); // trip_user_id 가져오기

        if(tripUserId == null) {
            return new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null);
        }

        tripMapper.updateSeqScheMemo(tripId, memoDto.getSeq(), false); // 등록할 메모의 다음 일정,메모들의 seq + 1
        memoMapper.postScheMemo(memoDto); // sche_memo 에 저장

        memoDto.setTripUserId(tripUserId); // dto 에 trip_user_id 세팅
        memoMapper.insMemo(memoDto); // memo 에 저장
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), memoDto.getScheMemoId());
    }



    // 메모 수정 권한
    @Transactional
    public ResponseEntity<ResponseWrapper<Integer>> updateMemo(MemoUpReq memoDto) {
        long signedUserId=authenticationFacade.getSignedUserId();
        if(signedUserId==0){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }
        MemoUpReq userIdAndTripUserId = memoMapper.selUserIdByMemoId(memoDto.getMemoId());

        if (userIdAndTripUserId.getUserId() != signedUserId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }
        memoDto.setTripUserId(userIdAndTripUserId.getTripUserId());
        memoMapper.patchMemo(memoDto);
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), 1));
    }


    // 메모 삭제 권한
    @Transactional
    public ResponseEntity<ResponseWrapper<Integer>> deleteMemo(Long memoId) {

        long signedUserId=authenticationFacade.getSignedUserId();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof JwtUser)){

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }
        Long memoOwnerId = memoMapper.findMemoOwnerId(memoId);

        if (!memoOwnerId.equals(signedUserId)) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
        }

        memoMapper.deleteMemoTest(memoId);
        memoMapper.deleteMemo(memoId);
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), 1));
    }
}













