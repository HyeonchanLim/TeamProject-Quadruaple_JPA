package com.green.project_quadruaple.review;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.review.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("review")
@Tag(name = "리뷰")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    @Operation(summary = "리뷰 조회")
    public List<ReviewSelRes> getReview(@RequestParam("strf_id") Long strfId,
                                        @RequestParam(value = "last_index",required = false) int lastIdx) {

        return reviewService.getReviewWithPics(strfId,lastIdx);
    }
    @GetMapping("my")
    @Operation(summary = "나의 리뷰 조회")
    public List<MyReviewSelRes> getMyReviews(@RequestParam("last_index") int lastIdx) {

        return reviewService.getMyReviews(lastIdx);
    }

    @PostMapping
    @Operation(summary = "리뷰 등록")
    public ResponseEntity<?> postReview(@RequestPart(required = false) List<MultipartFile> pics
                                        , @Valid @RequestPart ReviewPostReq p) {
        int result = reviewService.postRating(pics,p);
        if (result<0){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }


//    @PutMapping
//    @Operation(summary = "리뷰 수정")
//    public ResponseEntity<?> updateReview(@RequestPart List<MultipartFile> pics,
//                                                                 @Valid @RequestPart ReviewUpdReq p) {
//
//        ResponseEntity<ResponseWrapper<Integer>> response = reviewService.updateReview(pics, p);
//        return response;
//    }

    @DeleteMapping("del")
    @Operation(summary = "리뷰 삭제")
    public ResponseEntity<ResponseWrapper<Integer>> deleteReview(@RequestParam("review_id") Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }
}
