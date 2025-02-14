package com.green.project_quadruaple.trip;

import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.common.model.ResultResponse;
import com.green.project_quadruaple.trip.model.PubTransPathVo;
import com.green.project_quadruaple.trip.model.dto.IncompleteTripDto;
import com.green.project_quadruaple.trip.model.req.*;
import com.green.project_quadruaple.trip.model.res.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "여행")
public class TripController {

    private final TripService tripService;

    @GetMapping("/trip-list")
    @Operation(summary = "로그인 유저의 여행 목록 불러오기", description = "로그인 유저의 여행 목록 불러오기. after 가 완료된 여행, before 가 미완료된 여행")
    public ResponseWrapper<MyTripListRes> getTripList() {
        return tripService.getMyTripList();
    }

    @PostMapping("/trip")
    @Operation(summary = "여행 생성하기", description = "새로운 여행 생성하기. 일정은 X")
    public ResponseWrapper<PostTripRes> postTrip(@RequestBody PostTripReq req) {
        return tripService.postTrip(req);
    }

    @GetMapping("/trip/location")
    @Operation(summary = "여행 대분류 목록 불러오기", description = "처음 선택해야할 대분류(전국, 부산, 제주 등) 여행지 목록 불러오기")
    public ResponseWrapper<LocationRes> getLocationList() {
        return tripService.getLocationList();
    }

    @GetMapping("/trip")
    @Operation(summary = "여행 상세 정보 불러오기", description = "로그인유저의 특정 여행 상세정보(여행의 모든 일정 등) 불러오기")
    public ResponseWrapper<TripDetailRes> getTrip(@RequestParam("trip_id") @Schema(title = "여행 ID", example = "1") Long tripId) {
        return tripService.getTrip(tripId);
    }

    @PatchMapping("/trip")
    @Operation(summary = "여행 수정하기", description = "여행 타이틀 수정, 여행 일자 수정, 여행 장소 추가 삭제, 구성원 추가 삭제.")
    public ResultResponse patchTrip(@RequestBody PatchTripReq req) {
        return tripService.patchTrip(req);
    }

    @GetMapping("/add")
    @Operation(summary = "미완료 여행 목록 불러오기", description = "내 여행에 상품 담기 할때 어느 여행에 담을지 목록 출력")
    public ResponseWrapper<IncompleteTripRes> getIncomplete(@RequestParam("strf_id") long strfId) {
        return tripService.getIncomplete(strfId);
    }

    @GetMapping("/transport/get")
    @Operation(summary = "길 찾기", description = "출발지와 목적지 사이의 대중 교통(시외버스, 시내버스, 열차, 지하철 등)과 거리, 시간, 금액 정보 불러오기")
    public ResponseWrapper<List<FindPathRes>> getTransPort(@ParameterObject @ModelAttribute FindPathReq req) {
        return tripService.getTransPort(req);
    }

    @PostMapping("/schedule")
    @Operation(summary = "일정 등록", description = "선택한 상품과 이동정보를 DB 에 저장")
    public ResultResponse postSchedule(@RequestBody PostScheduleReq req) {
        return tripService.postSchedule(req);
    }

    @PatchMapping("/schedule")
    @Operation(summary = "일정 순서 변경", description = "선택한 일정의 순서를 변경. 변경된 순서로 이동 시간, 거리, 수단을 추천 경로로 임의로 변경")
    public ResultResponse patchSeq(@RequestBody PatchSeqReq req) {
        return tripService.patchSeq(req);
    }

    @DeleteMapping("/schedule")
    @Operation(summary = "일정 삭제", description = "선택한 일정을 삭제")
    public ResultResponse deleteSchedule(@RequestParam("schedule_id") long scheduleId) {
        return tripService.deleteSchedule(scheduleId);
    }

    @GetMapping("/trip/add-link")
    @Operation(summary = "구성원 추가 초대 키 생성", description = "로그인유저가 요청하는 여행ID의 구성원에 추가되는 URL 생성")
    public ResponseWrapper<String> getInviteKey(@RequestParam("trip_id") Long tripId) {
        return tripService.getInviteKey(tripId);
    }

    @PostMapping("/trip/user")
    @Operation(summary = "구성원 추가", description = "여행 초대 키 입력 페이지에서 공유받은 inviteKey 입력 시 로그인유저가 여행 구성원에 추가")
    public ResponseWrapper<Long> addTripUser(@RequestBody PostInviteKeyReq inviteKey) {
        return tripService.addTripUser(inviteKey.getInviteKey());
    }

    @DeleteMapping("/trip/user")
    @Operation(summary = "구성원 제외", description = "여행 구성원 제외하기, 여행 팀장과 본인만 가능")
    public ResultResponse deleteTripUser(@RequestBody DeleteTripUserReq req) {
        return tripService.deleteTripUser(req);
    }
}
