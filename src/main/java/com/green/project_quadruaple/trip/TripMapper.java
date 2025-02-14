package com.green.project_quadruaple.trip;

import com.green.project_quadruaple.trip.model.dto.*;
import com.green.project_quadruaple.trip.model.req.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripMapper {

    List<TripDto> getTripList(long userId);

    List<LocationDto> selLocationList();

    void insTrip(PostTripReq req);

    void insTripLocation(long tripId, List<Long> idList);

    Long selTripManagerId(Long tripId);
    boolean selExistsTripUser(Long tripId, Long userId);

    ScheCntAndMemoCntDto selScheduleCntAndMemoCnt(long tripId);
    List<TripDetailDto> selScheduleDetail(Long tripId, Long userId);
    List<TripDetailDto> selScheduleDetail(Long tripId);

    List<Long> selTripUserList(Long tripId);

    TripPeriodDto selTripPeriod(Long tripId);

    // patchTrip
    void updateTrip(PatchTripReq req);
    void insTripUser(long tripId, List<Long> userIdList);
    void delTripMemo(List<Long> scheduleIdList);
    void delTripUser(long tripId, List<Long> userIdList);
    void delTripScheMemo(List<Long> scheduleIdList);
    void delTripLocation(long tripId, List<Long> locationList);


    // getIncomplete
    List<Long> selScheduleUserId(long tripId, List<Long> userIdList);
    long selStrfLocationId(long strfId);
    List<TripIdMergeDto> selIncompleteTripList(long userId);

    // postSchedule
    Long existLocation(long tripId, long strfId);
    void insScheMemo(PostScheduleReq req);
    void insSchedule(PostScheduleReq req);
    ScheduleShortInfoDto selNextScheduleInfoByTripIdAndSeq(Long tripId, Integer seq);

    // deleteSchedule
    long selScheduleByScheduleId(long scheduleId);
    ScheduleDto selScheduleAndScheMemoByScheduleId(long scheduleId, long tripId);

    void updateSeqScheMemo(long tripId, int seq, boolean isDel);
    void updateSchedule(boolean isNotFirst, long nextScheduleId, int distance, int duration, int pathType);
    void deleteSchedule(long scheduleId);
    void deleteScheMemo(long scheduleId);

    // deleteTripUser
//    Long selTripById(long tripId);
    void disableTripUser(long tripId, long userId);
    List<StrfLatAndLngDto> selStrfLatAndLng(Long prevStrfId, Long nextStrfId);

    // patchSeq
    void updateSeq(long scheduleMemoId, int destSeq);
    void updateBetweenSeq(long tripId, long originSeq, long destSeq, boolean ahead);
    void updateDay(long scheduleMemoId, int destDay);
    LowAndHighSeqDto selScheMemoIdByTripIdAndSeq(long tripId, int seq);
}
