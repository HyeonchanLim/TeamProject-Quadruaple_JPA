package com.green.project_quadruaple.memo;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.entity.model.*;
import com.green.project_quadruaple.memo.model.Req.MemoPostReq;
import com.green.project_quadruaple.memo.model.Req.MemoUpReq;
import com.green.project_quadruaple.memo.model.Res.MemoGetRes;
import com.green.project_quadruaple.trip.ScheMemoRepository;
import com.green.project_quadruaple.trip.TripMapper;


import com.green.project_quadruaple.trip.TripRepository;
import com.green.project_quadruaple.trip.TripUserRepository;
import com.green.project_quadruaple.trip.model.ScheMemoType;
import com.green.project_quadruaple.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoMapper memoMapper;
    private final AuthenticationFacade authenticationFacade;
    private final TripMapper tripMapper;
    private final UserRepository userRepository;
    private final MemoRepository memoRepository;
    private final TripRepository tripRepository;
    private final TripUserRepository tripUserRepository;
    private final ScheMemoRepository scheMemoRepository;

    @Transactional
    public  ResponseEntity<ResponseWrapper<MemoGetRes>> getMemo(Long memoId) {
        long signedUserId=AuthenticationFacade.getSignedUserId();
        if(signedUserId==0){ return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));}
        MemoGetRes memo = memoMapper.selectMemo(memoId);

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(),memo));
    }


    @Transactional
    public ResponseWrapper<Long> addMemo(MemoPostReq p) {
        long signedUserId = Optional.of(AuthenticationFacade.getSignedUserId()).orElseThrow();

        Long tripId = p.getTripId();
        List<TripUser> tripUserList = tripUserRepository.findByTripId(tripId);
        TripUser tripUser = null;
        for (TripUser item : tripUserList) {
            if(item.getUser().getUserId() == signedUserId) {
                tripUser = item;
                break;
            }
        }
        if(tripUser == null) {
            return new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null);
        }

        ScheduleMemo scheMemo = ScheduleMemo.builder()
                .trip(tripUser.getTrip())
                .day(p.getDay())
                .seq(p.getSeq())
                .scheMemoType(ScheMemoType.MEMO)
                .build();
        Memo memo = Memo.builder()
                .scheduleMemo(scheMemo)
                .content(p.getContent())
                .tripUser(tripUser)
                .build();
        tripMapper.updateSeqScheMemo(tripId, p.getSeq(), false); // 등록할 메모의 다음 일정,메모들의 seq + 1

        scheMemoRepository.save(scheMemo);
        scheMemoRepository.flush();
        memoRepository.save(memo);
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), scheMemo.getScheduleMemoId());

    }

    // 메모 수정 권한
    @Transactional
    public ResponseEntity<ResponseWrapper<Integer>> updateMemo(MemoUpReq p) {
        Long signedUserId=authenticationFacade.getSignedUserId();
        User user = userRepository.findById(signedUserId).orElseThrow( () -> new RuntimeException("user id not found"));
        TripUser tripUser = tripUserRepository.findById(user.getUserId()).orElseThrow( () -> new RuntimeException("user id not found"));
        Trip trip = tripUser.getTrip();

        Memo memo = memoRepository.findById(p.getMemoId())
                .orElseThrow(() -> new RuntimeException("Memo not found for id: " + p.getMemoId()));

        memo.setContent(p.getContent());
        memo.setTripUser(tripUser); // 필요에 따라 TripUser를 설정

        memoRepository.save(memo); // 변경된 Memo 저장

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), 1));
//                ResponseWrapper<>(ResponseCode.OK.getCode(), 1);

//        if(signedUserId==0){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
//        }
//        MemoUpReq userIdAndTripUserId = memoMapper.selUserIdByMemoId(p.getMemoId());
//
//        if (userIdAndTripUserId.getUserId() != signedUserId) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
//        }
//        p.setTripUserId(userIdAndTripUserId.getTripUserId());
//        memoMapper.patchMemo(p);
//        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), 1));
    }


    // 메모 삭제 권한
    @Transactional
    public ResponseEntity<ResponseWrapper<Long>> deleteMemo(Long p) {
        Long signedUserId = authenticationFacade.getSignedUserId();

        User user = userRepository.findById(signedUserId).orElseThrow(() -> new RuntimeException("User ID not found"));

        TripUser tripUser = tripUserRepository.findById(user.getUserId()).orElseThrow(() -> new RuntimeException("TripUser not found"));

        Trip trip = tripUser.getTrip();

//        Long memoOwnerId = memoMapper.findMemoOwnerId(p);
//        if (!memoOwnerId.equals(signedUserId)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
//        }
        Memo memo = memoRepository.findById(p).orElseThrow(() -> new RuntimeException("Memo not found"));

        memoRepository.delete(memo);

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), p));


//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(!(principal instanceof JwtUser)){
//
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
//        }
//        Long memoOwnerId = memoMapper.findMemoOwnerId(p);
//
//        if (!memoOwnerId.equals(signedUserId)) {
//
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                    .body(new ResponseWrapper<>(ResponseCode.Forbidden.getCode(), null));
//        }
//
//        memoMapper.deleteMemoTest(p);
//        memoMapper.deleteMemo(p);
//        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), 1));
    }
}













