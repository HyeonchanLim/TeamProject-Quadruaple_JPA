package com.green.project_quadruaple.tripreview;

import com.green.project_quadruaple.tripreview.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripReviewMapper {
    //여행기 insert
    void insTripReview(TripReviewPostReq req);
    void insTripReviewPic(long tripReviewId, List<String> tripReviewPic);

    //여행기 select
    // 로그인한 사용자의 여행기 조회
    List<TripReviewGetDto> getMyTripReviews(long userId, String orderType);
    int getMyTripReviewsCount(long userId);
    // 모든 사용자의 여행기 조회
    int getTotalTripReviewsCount();
    List<TripReviewGetDto> getAllTripReviews(String orderType, int startIdx, int size);
    // 다른 사용자의 여행기 조회
    List<TripReviewGetDto> getOtherTripReviewById(long tripReviewId);

    //여행기 update
    int updTripReview(TripReviewPatchDto dto);
    void delTripReviewPic(long tripReviewId);

    //여행기 delete
    int delTripReview(long tripReviewId);
    TripReviewDeleteDto selTripReviewByUserId(long tripReviewId);
    void delTripReviewRecentTr(long tripReviewId);
    void delTripReviewScrap(long tripReviewId);
    void delTripReviewLikeByTripReviewId(long tripReviewId);

    //여행기 추천
    int insTripLike(TripLikeDto like);
    int delTripLike(TripLikeDto like);
    Integer tripLikeCount(Long tripReviewId);

    //여행기 조회수
    int insTripReviewRecent(long userId, long tripReviewId);

    //여행기 스크랩
    int insTripReviewScrap(TripReviewScrapDto scrap);
    int countTripReview(long tripReviewId, long tripId);
    int copyInsTrip(CopyInsertTripDto trip);
    int copyInsScheMemo(CopyInsertScheMemoDto scheMemo);
    int copyInsSchedule(CopyScheduleDto schedule);
    List<Long> getOriginalScheMemoIds(long tripId);
    List<Long> getOriginalScheduleIds(List<Long> scheduleMemoIds);
    List<Long> getNewScheMemoIds(long copyTripId);
    int getOriginalLocationIds(long tripId);
    void copyInsTripLocation(long copyTripId, long tripId);
}
