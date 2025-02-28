package com.green.project_quadruaple.review;

import com.green.project_quadruaple.entity.model.ReviewReply;
import com.green.project_quadruaple.review.model.BusinessDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/business/review")
@RequiredArgsConstructor
@Tag(name = "사업자 리뷰 대댓글")
public class BusinessReviewController {

   // 비즈니스 상품 및 리뷰 정보 조회 API

   private final BusinessReviewService businessReviewService;

    @GetMapping("/all")
    public ResponseEntity<List<BusinessDto>> getBusinessReviews(
            @RequestParam(name = "start_idx", defaultValue = "0") int startIdx) {

        List<BusinessDto> reviews = businessReviewService.getBusinessReview(startIdx);
        return ResponseEntity.ok(reviews);
    }




}
