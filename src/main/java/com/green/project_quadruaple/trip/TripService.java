package com.green.project_quadruaple.trip;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.trip.model.PathInfoVo;
import com.green.project_quadruaple.trip.model.PathType;
import com.green.project_quadruaple.trip.model.PathTypeVo;
import com.green.project_quadruaple.trip.model.PubTransPathVo;
import com.green.project_quadruaple.trip.model.req.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.green.project_quadruaple.common.config.constant.OdsayApiConst;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.common.model.ResultResponse;
import com.green.project_quadruaple.trip.model.dto.*;
import com.green.project_quadruaple.trip.model.res.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TripService {

    private final TripMapper tripMapper;
    private final OdsayApiConst odsayApiConst;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final WeatherApiCall weatherApiCall;

    private final String ADD_USER_LINK;

    public static final Map<String, Long> addUserLinkMap = new HashMap<>();

    public TripService(TripMapper tripMapper,
                       OdsayApiConst odsayApiConst,
                       WebClient webClient,
                       ObjectMapper objectMapper,
                       @Value("${add-user-link}") String ADD_USER_LINK,
                       WeatherApiCall weatherApiCall)
    {
        this.tripMapper = tripMapper;
        this.odsayApiConst = odsayApiConst;
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.ADD_USER_LINK = ADD_USER_LINK;
        this.weatherApiCall = weatherApiCall;
    }

    public ResponseWrapper<MyTripListRes> getMyTripList() {
        long signedUserId = Optional.of(AuthenticationFacade.getSignedUserId()).get();
        long now = new Date().getTime();
        List<TripDto> TripList = tripMapper.getTripList(signedUserId);

        List<TripDto> beforeTripList = new ArrayList<>();
        List<TripDto> afterTripList = new ArrayList<>();
        for (TripDto trip : TripList) {
            long tripEndTime = getMilliTime(trip.getEndAt());
            if(now > tripEndTime) {
                afterTripList.add(trip);
            } else {
                beforeTripList.add(trip);
            }
        }
        MyTripListRes res = new MyTripListRes();
        res.setBeforeTripList(beforeTripList);
        res.setAfterTripList(afterTripList);
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
    }

    public ResponseWrapper<LocationRes> getLocationList() {
        List<LocationDto> dto = tripMapper.selLocationList();
        LocationRes res = new LocationRes();
        res.setLocationList(dto);
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
    }

    public ResponseWrapper<PostTripRes> postTrip(PostTripReq req) {
        long signedUserId = Optional.of(AuthenticationFacade.getSignedUserId()).get();
        req.setManagerId(signedUserId);
        tripMapper.insTrip(req);
        tripMapper.insTripUser(req.getTripId(), List.of(signedUserId));
        tripMapper.insTripLocation(req.getTripId(), req.getLocationId());
        PostTripRes res = new PostTripRes();
        res.setTripId(req.getTripId());
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
    }

    /*
    * 여행 상세 정보 불러우기 getTrip
    * */
    public ResponseWrapper<TripDetailRes> getTrip(Long tripId) {
        Long signedUserId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof JwtUser) {
            signedUserId = AuthenticationFacade.getSignedUserId();
        }

//        Long signedUserId = Optional.of(AuthenticationFacade.getSignedUserId()).get();
        ScheCntAndMemoCntDto scAndMcAndTripInfoDto = tripMapper.selScheduleCntAndMemoCnt(tripId);

        if(scAndMcAndTripInfoDto == null) {
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode() + " 여행이 존재하지 않음", null);
        }

        TripPeriodDto tripPeriod = tripMapper.selTripPeriod(tripId);
        if(tripPeriod == null) {
            return new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 문자열을 LocalDate로 변환
        LocalDate startDate = LocalDate.parse(tripPeriod.getStartAt(), formatter);
        LocalDate endDate = LocalDate.parse(tripPeriod.getEndAt(), formatter);

        // 두 날짜의 차이를 계산
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        if(daysBetween >= 0) {
            daysBetween += 1;
        }

        List<TripDetailDto> tripDetailDto;
        if(signedUserId == null) {
            tripDetailDto = tripMapper.selScheduleDetail(tripId);
        } else {
             tripDetailDto = tripMapper.selScheduleDetail(tripId, signedUserId);
        }
        List<Long> tripUserIdList = tripMapper.selTripUserList(tripId);
        long totalDistance = 0L;
        long totalDuration = 0L;

        TripDetailRes res = new TripDetailRes();
        res.setScheduleCnt(scAndMcAndTripInfoDto.getScheduleCnt());
        res.setMemoCnt(scAndMcAndTripInfoDto.getMemoCnt());
        res.setTripId(scAndMcAndTripInfoDto.getTripId());
        res.setTitle(scAndMcAndTripInfoDto.getTitle());
        res.setStartAt(scAndMcAndTripInfoDto.getStartAt());
        res.setEndAt(scAndMcAndTripInfoDto.getEndAt());
        res.setTripUserIdList(tripUserIdList);
        res.setTripLocationList(scAndMcAndTripInfoDto.getTripLocationList());
        if(tripDetailDto.isEmpty()) {
            ArrayList<TripDetailDto> days = new ArrayList<>();
            for (int i = 1; i <=daysBetween; i++) {
                TripDetailDto dto = new TripDetailDto();
                dto.setDay(i);
                dto.setSchedules(new ArrayList<>());
                days.add(dto);
            }
            res.setDays(days);
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
        }
        for (TripDetailDto detailDto : tripDetailDto) {
//            detailDto.setWeather("sunny"); // 날씨 API 받아와야함
            List<ScheduleResDto> schedules = detailDto.getSchedules();
            ScheduleResDto weatherSchedule = null;
            for (ScheduleResDto schedule : schedules) {
                if(weatherSchedule == null) {
                    if(schedule.getScheOrMemo().equals("SCHE")) {
                            weatherSchedule = schedule;
                    }
                }
                Long distance = schedule.getDistance();
                Long duration = schedule.getDuration();
                if(distance == null || duration == null) {
                    continue;
                }
                totalDistance += distance;
                totalDuration += duration;
            }
            if(weatherSchedule != null) {
                detailDto.setWeather(weatherApiCall.call(webClient, objectMapper, weatherSchedule.getLat(), weatherSchedule.getLng()));
            }
        }


        res.setDays(tripDetailDto);
        res.setTotalDistance(totalDistance);
        res.setTotalDuration(totalDuration);


        List<TripDetailDto> resTripDetail = new ArrayList<>();
        boolean flag = false;
        for(int i=1; i<=daysBetween; i++) {
            for (TripDetailDto detailDto : tripDetailDto) {
                if(detailDto.getDay() == i) {
                    resTripDetail.add(detailDto);
                    flag = true;
                }
            }
            if(flag) {
                flag = false;
                continue;
            }
            TripDetailDto nullDataWithDay = new TripDetailDto();
            nullDataWithDay.setDay(i);
            nullDataWithDay.setSchedules(new ArrayList<>());
            resTripDetail.add(nullDataWithDay);
        }
        log.info("trip detail = {}", resTripDetail);

        res.setDays(resTripDetail);

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
    }

    /*
    * 여행 수정
    * */
    public ResultResponse patchTrip(PatchTripReq req) {
        long signedUserId = Optional.of(AuthenticationFacade.getSignedUserId()).get();
        long tripId = req.getTripId();
        long managerId = tripMapper.selTripManagerId(req.getTripId());
        if(signedUserId != managerId) {
            return ResultResponse.forbidden();
        }
        tripMapper.updateTrip(req);

        // 참여 유저 수정
        if(req.getInsUserList() != null && !req.getInsUserList().isEmpty()) {
            tripMapper.insTripUser(tripId, req.getInsUserList());
        }

        if(req.getDelUserList() != null && !req.getDelUserList().isEmpty()) {
            List<Long> scheduleUserIdList = tripMapper.selScheduleUserId(tripId, req.getDelUserList());
            if(scheduleUserIdList != null && !scheduleUserIdList.isEmpty()) {
                tripMapper.delTripMemo(scheduleUserIdList);
                tripMapper.delTripScheMemo(scheduleUserIdList);
            }
            tripMapper.delTripUser(tripId, req.getDelUserList());
        }

        if(req.getInsLocationList() != null && !req.getInsLocationList().isEmpty()) {
            tripMapper.insTripLocation(tripId, req.getInsLocationList());
        }

        if(req.getDelLocationList() != null && !req.getDelLocationList().isEmpty()) {
            tripMapper.delTripLocation(tripId, req.getDelLocationList());
        }

        // 여행 지역 수정
        return ResultResponse.success();
    }

    /*
     * 1. 요청 값인 상품ID 로 DB 에서 상품의 locationId 를 찾기
     * 2. 로그인 유저의 여행 리스트 데이터 DB 에서 가져오기
     * 3. 여행 리스트 중 현재 시간보다 endAt 이 미래라면 미완료 여행으로 파악.
     * 4. 미완료 여행의 총 일차를 계산해서 totalDay 에 담음
     * 5. 미완료 여행 목록 중 상품의 locationId 를 이미 가지고 있는 여행이라면 locateTripList 에 저장, 아니라면 totalTripList 에 저장
     * */
    public ResponseWrapper<IncompleteTripRes> getIncomplete(long strfId) {
        long signedUserId = Optional.of(AuthenticationFacade.getSignedUserId()).get();
        long strfLocationId = tripMapper.selStrfLocationId(strfId); // 상품의 locationId 찾기
        long now = new Date().getTime();
        List<TripIdMergeDto> dtoList = tripMapper.selIncompleteTripList(signedUserId); // 로그인 유저의 여행 리스트 데이터 DB 에서 가져오기
        if(dtoList == null || dtoList.isEmpty()) { // 일정 없으면 return
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), null);
        }

        IncompleteTripRes res = new IncompleteTripRes();
        List<IncompleteTripDto> locateTripList = new ArrayList<>();
        List<IncompleteTripDto> totalTripList = new ArrayList<>();
        for (TripIdMergeDto groupId : dtoList) {
            IncompleteTripDto temp = null;
            long endAt = getMilliTime(groupId.getIncompleteTripList().get(0).getEndAt());
            if(now > endAt) continue; // 해당 여행의 endAt 이 현재시간보다 과거일시 완료된 여행이므로 넘김
            for (IncompleteTripDto dto : groupId.getIncompleteTripList()) { // tripId 를 그룹으로 잡은 리스트
                if(dto.getLocationId() == strfLocationId) {
                    temp = dto;
                    break;
                } else {
                    temp = dto;
                }
            }
            if(temp == null) break;
            temp.setTotalDay(totalDay(temp.getStartAt(), temp.getEndAt())); // 총 일자 계산해서 totalDay 에 저장
            if(temp.getLocationId() == strfLocationId) { // 상품의 locationId 를 이미 가지고 있는 여행이라면 locateTripList 에 저장, 아니라면 totalTripList 에 저장
                locateTripList.add(temp);
            } else {
                totalTripList.add(temp);
            }
        }
        res.setMatchTripId(locateTripList);
        res.setNoMatchTripId(totalTripList);
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
    }

    /*
    * shcedule 테이블에 상품(일정)을 저장. 이동수단과 거리는 미정
    * trip_location 테이블에 상품의 location 이 존재하지 않으면 저장.
    * */
//    public ResultResponseUser postIncomplete(PostStrfScheduleReq req) {
//        long tripId = req.getTripId();
//        long strfId = req.getStrfId();
//        Long existLocation = tripMapper.existLocation(tripId, strfId);
//
//        if(existLocation == null) {
//            long locationId = tripMapper.selStrfLocationId(strfId);
//            tripMapper.insTripLocation(tripId, List.of(locationId));
//        }
//        tripMapper.insScheMemo(req);
//        tripMapper.insSchedule(req);
//        return ResultResponseUser.success();
//    }

    /*
    * 길찾기
    * */
    public ResponseWrapper<List<FindPathRes>> getTransPort(FindPathReq req) {
        Optional.of(AuthenticationFacade.getSignedUserId()).get();
        log.info("odsayConst.getBaseUrl = {}", odsayApiConst.getBaseUrl());
        log.info("odsayConst.getSearchPubTransPathUrl = {}", odsayApiConst.getSearchPubTransPathUrl());
        String json = httpPostRequestReturnJson(req);

        log.info("길찾기 json = {}", json);
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            PubTransPathVo pathVo =  objectMapper.convertValue(jsonNode.at("/result")
                    , new TypeReference<>() {});
            log.info("pathVo = {}", pathVo);
            if(pathVo == null || pathVo.getPath() == null) {
                return new ResponseWrapper<>(ResponseCode.WRONG_XY_VALUE.getCode(), null);
            }
            List<FindPathRes> res = new ArrayList<>();
            for (PathTypeVo pathList : pathVo.getPath()) { // path 리스트
                FindPathRes path = new FindPathRes(); // res에 담을 객체 생성
                PathInfoVo info = pathList.getInfo();
                String pathName = Optional.ofNullable(PathType.getKeyByValue(pathList.getPathType())).orElse(PathType.WALK).getName();
                path.setPathType(pathName);
                path.setTotalTime(info.getTotalTime());
                path.setTotalDistance(info.getTotalDistance());
                if(pathVo.getSearchType() == 0) { // 도시내 이동
                    path.setPayment(pathList.getInfo().getPayment());
                } else { // 도시와 도시간 이동
                    path.setPayment(pathList.getInfo().getTotalPayment());
                }
                res.add(path);
            }
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), res);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>(ResponseCode.SERVER_ERROR.getCode(), null);
        }
    }

    /*
    * 일정 등록
    * 1. trip_location 에 등록할 일정의 strf 의 location 이 없다면 등록.
    * 2. 여행ID 가 일치하고 Seq 값이 postSche 보다 높은 일정들의 Seq 값을 1 증가
    * 3. 등록할 일정(postSche)의 seq 보다 1 높은 일정(nextSche)을 가져오기. nextSche 가 null 이면 변경 없음.
    * 4. 가져온 nextSche 의 strf 위경도(start)와 postSche 의 위경도(end)로 OdsayAPi 호출, 거리, 시간, 수단 불러오기
    * 5. nextSche 의 거리, 시간, 수단을 가져온 API 값으로 update
    * */
    public ResultResponse postSchedule(PostScheduleReq req) {
        long signedUserId = AuthenticationFacade.getSignedUserId();
        long tripId = req.getTripId();
        if(!tripMapper.selExistsTripUser(tripId, signedUserId)) {
            return ResultResponse.forbidden();
        }
        long strfId = req.getStrfId();
        int seq = req.getSeq();

        // seq 가 1인데 시간, 거리, 수단이 있으면 안됌.
        if(seq == 1 && (req.getDistance() != null || req.getTime() != null || req.getPathType() != null)) {
            return ResultResponse.badRequest();
        }

        long locationId = tripMapper.selStrfLocationId(strfId);
        tripMapper.insTripLocation(tripId, List.of(locationId)); // 등록할 일정의 상품 지역이 trip_locatin 에 등록되어 있지 않다면 등록

        PathType keyByName = PathType.getKeyByName(req.getPathType());
        if(keyByName != null) {
            req.setPathTypeValue(keyByName.getValue()); // 버스 -> 2 로 바꿈
        }

        tripMapper.updateSeqScheMemo(tripId, seq, false); // 등록할 일정 보다 seq 가 높은 일정들의 seq+1
        tripMapper.insScheMemo(req);
        tripMapper.insSchedule(req);

        ScheduleShortInfoDto nextScheInfo = tripMapper.selNextScheduleInfoByTripIdAndSeq(tripId, seq);
        log.info("nextScheInfo = {}", nextScheInfo);
        if (nextScheInfo == null) {
            return ResultResponse.success();
        }
        // 4. 가져온 nextSche 의 strf 위경도(start)와 postSche 의 위경도(end)로 OdsayAPi 호출, 거리, 시간, 수단 불러오기
        List<StrfLatAndLngDto> strfLatAndLngDtos = tripMapper.selStrfLatAndLng(strfId, nextScheInfo.getStrfId());
        log.info("strfLatAndLngDtos = {}", strfLatAndLngDtos);
        FindPathReq findPath = new FindPathReq();
        setOdsayParams(findPath, strfLatAndLngDtos, strfId);
        String json = httpPostRequestReturnJson(findPath);
        log.info("odsay json = {}", json);
        PathTypeVo firstPathType = getFirstPathType(json);
        PathInfoVo pathTypeInfo = firstPathType.getInfo();

        // nextSche 의 거리, 시간, 수단을 가져온 API 값으로 update
        tripMapper.updateSchedule(true, nextScheInfo.getScheduleId(), pathTypeInfo.getTotalDistance(), pathTypeInfo.getTotalTime(), firstPathType.getPathType());

        return ResultResponse.success();
    }

    /*
    * 일정메모 순서 변경
    * */
    public ResultResponse patchSeq(PatchSeqReq req) {
        long signedUserId = Optional.of(AuthenticationFacade.getSignedUserId()).get();
        long tripId = req.getTripId();
        Long managerId = tripMapper.selTripManagerId(tripId);
        if(signedUserId != managerId) {
            return ResultResponse.forbidden();
        }
        long scheduleId = req.getScheduleId();
        int originSeq = req.getOriginSeq();
        int destSeq = req.getDestSeq();
        Integer destDay = req.getDestDay();
        boolean ahead = false;

        try {
            ScheduleDto scheduleDto = tripMapper.selScheduleAndScheMemoByScheduleId(scheduleId, tripId);
            boolean notFirst = scheduleDto.isNotFirst();

            if(originSeq > destSeq) {
                // 목적지 seq 가 기존 seq 보다 작은 값일 경우 앞으로 이동으로 간주.
                // 기존 seq-1 과 목적지 seq 사이의 seq 를 전부 +1
                ahead = true;
                originSeq -= 1;
            } else {
                // 반대의 경우, 기존 seq+1 과 목적지 seq 사이의 seq 를 전부 -1
                originSeq += 1;
            }
            tripMapper.updateBetweenSeq(tripId, originSeq, destSeq, ahead);
            tripMapper.updateSeq(scheduleId, destSeq);
            if(destDay != null) { // destDay 가 있다면 DB 수정
                tripMapper.updateDay(scheduleId, destDay);
            }

            if(scheduleDto.getScheOrMemo().equals("MEMO")) {
                log.info("메모 변경 완료");
                return ResultResponse.success();
            }

            /*
            * 기본 로직
            * 1. (완료)A의 원래 자리의 다음 일정 거리, 시간, 수단을 원래 자리의 이전 일정 위치로 계산.
            * 2. A의 변경된 위치의 이전 일정과 거리, 시간, 수단을 재 계산
            * 3. A의 변경된 위치의 다음 일정의 거리, 시간, 수단을 재 계산
            *
            * A의 원래 자리가 첫 일정이라면 (위치가 변동된 일정은 A)
            * 1-1. A의 원래 자리의 다음 일정 거리, 시간, 수단을 NULL 로 변경
            *
            * A의 원래 자리가 마지막 일정이라면
            * 1-1. 변경 없음
            *
            * A의 변경된 위치가 첫 일정이라면
            * 2-1. 변경 없음
            *
            * A의 변경된 위치가 마지막 일정이라면
            * 3-1. 변경 없음
            * */
            if(!notFirst) { // 원래 자리가 첫 일정이라면
                tripMapper.updateSchedule(false, scheduleDto.getNextScheduleId(), 0, 0, 0);
            } else if (scheduleDto.getNextScheduleStrfId() != null) { // 원래 자리가 마지막 일정이 아니라면
                // 원래 자리의 다음 일정 거리, 시간, 수단을 원래 자리의 이전 일정 위치로 계산.
                setSchedulePath(true, scheduleDto.getPrevScheduleStrfId(), scheduleDto.getNextScheduleStrfId(), scheduleDto.getNextScheduleId());
            }

            // 여기서 부터는 목적지의 변경
            scheduleDto = tripMapper.selScheduleAndScheMemoByScheduleId(scheduleId, tripId); // 변경된 위치의 정보로 새로 불러옴
            if(!scheduleDto.isNotFirst()) {
                tripMapper.updateSchedule(false, scheduleDto.getScheduleMemoId(), 0, 0, 0);
            } else if(scheduleDto.getNextScheduleStrfId() != null) {
                // A의 변경된 위치의 다음 일정의 거리, 시간, 수단을 재 계산
                setSchedulePath(true, scheduleDto.getStrfId(), scheduleDto.getNextScheduleStrfId(), scheduleDto.getNextScheduleId());

                // A의 변경된 위치의 이전 일정과 거리, 시간, 수단을 재 계산
                setSchedulePath(true, scheduleDto.getPrevScheduleStrfId(), scheduleDto.getStrfId(), scheduleId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        log.info("일정 순서 변경 완료");
        return ResultResponse.success();
    }

    /*
    * 일정의 거리, 시간, 수단을 updatae
    * */
    private void setSchedulePath(boolean isNotFirst, Long prevScheduleStrfId, Long nextScheduleStrfId, Long nextScheduleId) {
        List<StrfLatAndLngDto> strfLatAndLngDtoList = tripMapper.selStrfLatAndLng(prevScheduleStrfId, nextScheduleStrfId);
        FindPathReq findPathReq = new FindPathReq();
        setOdsayParams(findPathReq, strfLatAndLngDtoList, prevScheduleStrfId);
        String json = httpPostRequestReturnJson(findPathReq);
        PathTypeVo firstPath = getFirstPathType(json);
        int pathType = firstPath.getPathType();
        int distance = firstPath.getInfo().getTotalDistance();
        int duration = firstPath.getInfo().getTotalTime();
        tripMapper.updateSchedule(isNotFirst, nextScheduleId, distance, duration, pathType);
    }

    /*
    * 일정 삭제
    * 1. 만약 첫번째 일정을 삭제한다면 다음으로 첫번째 일정이 되는 일정의 시간, 거리, 이동수단을 null 로 바꿔야함
    * 2. 삭제하는 일정의 seq 보다 seq가 높은 일정+메모들의 seq 를 모두 -1 해주어야함
    * 3. sche_memo 의 category 가 SCHE 인 가장 가까운 seq 의 일정은 거리, 시간, 이동수단을 삭제하는 일정의 이전 일정과 다시 맞추어야함
    * 4. 먄약 삭제하는 일정이 마지막 일정이라면 그냥 일정만 삭제하면 됨
    * */
    public ResultResponse deleteSchedule(long scheduleId) {
        long signedUserId = Optional.of(AuthenticationFacade.getSignedUserId()).get();

        try {
            long tripId = tripMapper.selScheduleByScheduleId(scheduleId); // 삭제할 일정의 여행 ID
            Long managerId = tripMapper.selTripManagerId(tripId);
            if(signedUserId != managerId) {
                return ResultResponse.forbidden();
            }
            ScheduleDto scheduleDto = tripMapper.selScheduleAndScheMemoByScheduleId(scheduleId, tripId); // 삭제할 일정
            if(!scheduleDto.isNotFirst()) { // 첫번째 일정일때는 다음일정의 거리, 시간, 이동수단을 null 로 바꾸고 끝.
                tripMapper.updateSeqScheMemo(scheduleDto.getTripId(), scheduleDto.getSeq(), true); // ScheMemo 의 시퀀스 변경
                tripMapper.updateSchedule(scheduleDto.isNotFirst(), scheduleDto.getNextScheduleId(), 0, 0, 0); // 삭제한 일정의 다음 일정 정보 수정
                tripMapper.deleteSchedule(scheduleId); // schedule 삭제
                tripMapper.deleteScheMemo(scheduleId); // ScheMemo 삭제
                return ResultResponse.success();
            }
            List<StrfLatAndLngDto> prevAndNextStrfLatAndLng = tripMapper.selStrfLatAndLng(scheduleDto.getPrevScheduleStrfId(), scheduleDto.getNextScheduleStrfId()); // 이걸로 API 요청
            FindPathReq params = new FindPathReq();
            setOdsayParams(params, prevAndNextStrfLatAndLng, scheduleDto.getPrevScheduleStrfId());
            String json = httpPostRequestReturnJson(params);

            PathTypeVo firstPath = getFirstPathType(json);
            int pathType = firstPath.getPathType();
            int distance = firstPath.getInfo().getTotalDistance();
            int duration = firstPath.getInfo().getTotalTime();

            tripMapper.updateSeqScheMemo(scheduleDto.getTripId(), scheduleDto.getSeq(), true); // ScheMemo 의 시퀀스 변경
            tripMapper.updateSchedule(scheduleDto.isNotFirst(), scheduleDto.getNextScheduleId(), distance, duration, pathType); // 삭제한 일정의 다음 일정 정보 수정
            tripMapper.deleteSchedule(scheduleId); // schedule 삭제
            tripMapper.deleteScheMemo(scheduleId); // ScheMemo 삭제
            return ResultResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultResponse.severError();
        }
    }

    public ResponseWrapper<String> getInviteKey(Long tripId) {
        long signedUserId = AuthenticationFacade.getSignedUserId();
        if(signedUserId != tripMapper.selTripManagerId(tripId)) {
            throw new RuntimeException("여행의 팀장이 아님");
        }
        String uuid = UUID.randomUUID().toString();
        addUserLinkMap.put(uuid, tripId);
        Runnable addUserLinkThread = new AddUserLinkThread(uuid);
        new Thread(addUserLinkThread).start();

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), uuid);
    }

    public ResponseWrapper<Long> addTripUser(String uuid) {
        Long signedUserId = AuthenticationFacade.getSignedUserId();
        try {
            Long tripId = addUserLinkMap.get(uuid);
            if(tripId == null) {
                return new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), null);
            }
            tripMapper.insTripUser(tripId, List.of(signedUserId));
            return new ResponseWrapper<>(ResponseCode.OK.getCode(), tripId); // 리다이렉션 필요
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ResultResponse deleteTripUser(DeleteTripUserReq req) {
        long signedUserId = Optional.of(AuthenticationFacade.getSignedUserId()).get();
        long tripId = req.getTripId();
        if(signedUserId != tripMapper.selTripManagerId(tripId)) {
            return ResultResponse.forbidden();
        }

        long targetUserId = req.getTargetUserId();

        Long managerId = tripMapper.selTripManagerId(tripId);
        if(managerId != req.getLeaderId() || signedUserId != targetUserId || managerId == targetUserId) {
            return ResultResponse.forbidden();
        }

        tripMapper.disableTripUser(tripId, targetUserId);
        return ResultResponse.success();
    }

    private long getMilliTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private long totalDay(String startAt, String endAt) {

        LocalDate startDate = LocalDate.of(
                Integer.parseInt(startAt.substring(0, 4)),
                Integer.parseInt(startAt.substring(5, 7)),
                Integer.parseInt(startAt.substring(8, 10)));

        LocalDate endDate = LocalDate.of(
                Integer.parseInt(endAt.substring(0, 4)),
                Integer.parseInt(endAt.substring(5, 7)),
                Integer.parseInt(endAt.substring(8, 10)));

        return ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    /*
    * String 형태의 JSON 데이터 가져오기
    * A -> B 까지의 시간, 거리, 수단, 금액
    * */
    private String httpPostRequestReturnJson(FindPathReq req) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add(odsayApiConst.getParamApiKeyName(), odsayApiConst.getParamApiKeyValue());
        formData.add(odsayApiConst.getParamStartLatName(), req.getStartLatSY());
        formData.add(odsayApiConst.getParamStartLngName(), req.getStartLngSX());
        formData.add(odsayApiConst.getParamEndLatName(), req.getEndLatEY());
        formData.add(odsayApiConst.getParamEndLngName(), req.getEndLngEX());

        return webClient.post()
                .uri(odsayApiConst.getSearchPubTransPathUrl())
                .body(BodyInserters.fromFormData(formData))
                .retrieve() //통신 시도
                .bodyToMono(String.class) // 결과물을 String변환
                .block(); //비동기 > 동기
    }

    /*
    * FindPathReq 객체에 Odsay API 요청 파라미터 세팅
    * */
    private void setOdsayParams(FindPathReq params, List<StrfLatAndLngDto> LatLngDtoList, long prevScheduleStrfId) {
        for(StrfLatAndLngDto strfLatAndLngDto : LatLngDtoList) {
            if(strfLatAndLngDto.getStrfId() == prevScheduleStrfId) {
                params.setStartLngSX(strfLatAndLngDto.getLng());
                params.setStartLatSY(strfLatAndLngDto.getLat());
            } else {
                params.setEndLngEX(strfLatAndLngDto.getLng());
                params.setEndLatEY(strfLatAndLngDto.getLat());
            }
        }
    }

    /*
    * JSON 데이터의 첫번쨰 경로 가져오기
    * */
    private PathTypeVo getFirstPathType(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            PubTransPathVo pathVo =  objectMapper.convertValue(jsonNode.at("/result")
                    , new TypeReference<>() {});
            log.info("pathVo = {}", pathVo);
            if(pathVo.getPath() == null) {
                throw new RuntimeException();
            }
            return pathVo.getPath().get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
