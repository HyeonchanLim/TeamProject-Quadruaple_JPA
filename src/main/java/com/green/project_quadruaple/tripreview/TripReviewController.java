package com.green.project_quadruaple.tripreview;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.tripreview.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("trip-review")
@Tag(name = "여행기")
public class TripReviewController {
    private final TripReviewService tripReviewService;

    //여행기 등록
    @PostMapping
    @Operation(summary = "여행기 등록")
    public ResponseEntity<?> postTripReview(@RequestPart(required = false) List<MultipartFile> tripReviewPic, @RequestPart TripReviewPostReq req){
        try {
            TripReviewPostRes res = tripReviewService.postTripReview(tripReviewPic, req);
            if (res == null || tripReviewPic == null) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), null));
            }
            return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), res));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("여행이 시작되기 전에 여행기를 등록할 수 없습니다.")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(ResponseCode.BAD_REQUEST.getCode(), null));
            } else if (e.getMessage().contains("ScheMemo not fount")) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseWrapper<>(ResponseCode.NOT_Acceptable.getCode(), null));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseCode.SERVER_ERROR.getCode());
            }
        }
    }

    //여행기 조회
    @GetMapping("myTripReview")
    @Operation(summary = "내 여행기 조회", description = "likeCount(추천수), recentCount(조회수), scrapCount(스크랩수)")
    public ResponseEntity<?> getMyTripReview(@Parameter(name = "orderType", description = "정렬 방식 (latest: 최신순, popular: 추천순)", example = "latest", in = ParameterIn.QUERY)
                                                 @RequestParam(defaultValue = "latest") String orderType) {
        List<TripReviewGetDto> myTripReview = tripReviewService.getMyTripReviews(orderType);

        if (myTripReview == null || myTripReview.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), "등록된 여행기가 없습니다."));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), myTripReview));
    }
    @GetMapping("myTripReviewCount")
    @Operation(summary = "내 여행기 개수")
    public ResponseEntity<?> getMyTripReviewCount() {
        int result = tripReviewService.getMyTripReviewsCount();
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @GetMapping("allTripReview")
    @Operation(summary = "모든 여행기 조회", description = "likeCount(추천수), recentCount(조회수), scrapCount(스크랩수)")
    public ResponseEntity<ResponseWrapper<TripReviewGetResponse>> getAllTripReview(
            @Parameter(name = "orderType", description = "정렬 방식 (latest: 최신순, popular: 추천순)", example = "latest", in = ParameterIn.QUERY)
            @RequestParam(defaultValue = "latest") String orderType,

            @Parameter(name = "pageNumber", description = "페이지 번호 (1부터 시작)", example = "1", in = ParameterIn.QUERY)
            @RequestParam(defaultValue = "1") int pageNumber) {  // size 파라미터 제거

        TripReviewGetResponse allTripReview = tripReviewService.getAllTripReviews(orderType, pageNumber);

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), allTripReview));
    }

    @GetMapping("allTripReviewCount")
    @Operation(summary = "모든 여행기 개수")
    public ResponseEntity<?> getAllTripReviewCount() {
        int result =  tripReviewService.getTripReviewCount();
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

        @GetMapping("otherTripReview")
        @Operation(summary = "다른 사용자의 여행기 조회", description = "likeCount(추천수), recentCount(조회수), scrapCount(스크랩수), likeUser(로그인한 유저의 해당 여행기 추천 여부/1이면 추천,0이면 비추천)")
        public ResponseEntity<?> getOtherTripReview(@RequestParam long tripReviewId) {
            List<TripReviewGetDto> tripReviewGetDto = tripReviewService.getOtherTripReviews(tripReviewId);

            if (tripReviewGetDto.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
            }
            return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), tripReviewGetDto));
        }

    //여행기 수정
    @PatchMapping
    @Operation(summary = "여행기 수정")
    public ResponseEntity<?> patchTripReview(@RequestPart(required = false) List<MultipartFile> tripReviewPic, @RequestPart TripReviewPatchDto dto) {
        int result = tripReviewService.patchTripReview(tripReviewPic, dto);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    //여행기 삭제
    @DeleteMapping
    @Operation(summary = "여행기 삭제")
    public ResponseEntity<?> deleteTripReview(@RequestParam long tripReviewId) {
        int result = tripReviewService.deleteTripReview(tripReviewId);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(),null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    //여행기 추천
    @PostMapping("like")
    @Operation(summary = "여행기 추천 등록")
    public ResponseEntity<?> insTripLike(@RequestBody TripLikeDto like) {
        try {
            int result = tripReviewService.insTripLike(like);
            if (result == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
            }
            return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("이미 좋아요를 눌렀습니다.")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseCode.BAD_REQUEST.getCode());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseCode.SERVER_ERROR.getCode());
            }
        }
    }

    @DeleteMapping("like")
    @Operation(summary = "여행기 추천 취소")
    public ResponseEntity<?> delTripLike(@RequestBody TripLikeDto like) {
        int result = tripReviewService.delTripLike(like);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @GetMapping("like/count")
    @Operation(summary = "여행기 추천 수")
    public ResponseEntity<?> countLike(@RequestParam Long tripReviewId) {
        int result = tripReviewService.getTripLikeCount(tripReviewId);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>("해당 여행기에 추천이 없습니다.", null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    //--------------------------------------------------------------------
    // 여행기 스크랩
    @PostMapping("scrap")
    @Operation(summary = "여행기 스크랩 등록")
    public ResponseEntity<?> postScrap(@RequestBody CopyInsertTripDto trip) {
        int result = tripReviewService.copyTripReview(trip);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }

        // 새로 생성된 tripId 포함
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("result", result);
        responseData.put("tripId", trip.getTripId());

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), responseData));
    }
}
