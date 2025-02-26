package com.green.project_quadruaple.review;

import com.green.project_quadruaple.review.model.BusinessDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/business/reviews")
@RequiredArgsConstructor
@Tag(name = "사업자 리뷰 대댓글")
public class BusinessReviewController {
    private final BusinessReviewService businessService;

    // 비즈니스 상품 및 리뷰 정보 조회 API

    private final BusinessReviewService businessReviewService;

    @GetMapping("/all")
    public ResponseEntity<List<BusinessDto>> getBusinessReviews(
            @RequestParam(defaultValue = "0") int startIdx,
            @RequestParam(defaultValue = "10") int pageSize) {

        List<BusinessDto> reviews = businessReviewService.getBusinessReviews(startIdx, pageSize);
        return ResponseEntity.ok(reviews);
    }
}
