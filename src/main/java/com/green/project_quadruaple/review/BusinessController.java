package com.green.project_quadruaple.review;

import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.review.model.BusinessDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/business")
@RequiredArgsConstructor
@Tag(name = "사업자 리뷰 대댓글")
public class BusinessController {
    private final BusinessService businessService;

    // 비즈니스 상품 및 리뷰 정보 조회 API

    // 전체 리뷰 조회 API (로그인 유저 기반)
    @GetMapping("/reviews/all")
    public ResponseEntity<?> getAllBusinessReviews(@RequestParam(defaultValue = "0") int startIdx) {
        try {
            List<BusinessDto> reviews = businessService.getAllBusinessReviews(startIdx);
            return ResponseEntity.ok(reviews);
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }
}
