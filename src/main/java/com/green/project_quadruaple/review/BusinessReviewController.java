package com.green.project_quadruaple.review;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.review.model.BusinessDto;
import com.green.project_quadruaple.review.reviewReply.ReviewReplyDeleteDto;
import com.green.project_quadruaple.review.model.ReviewReplyReq;
import com.green.project_quadruaple.review.reviewReply.ReviewReplyPostDto;
import com.green.project_quadruaple.review.reviewReply.ReviewReplyUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/business/review")
@RequiredArgsConstructor
@Tag(name = "사업자 리뷰 대댓글")
public class BusinessReviewController {


   // 비즈니스 상품 및 리뷰 정보 조회 API
   private final BusinessReviewService businessReviewService;

    /**
     * 리뷰 댓글 생성 (사업자만 가능)
     */
    @GetMapping("/all")
    public ResponseEntity<List<BusinessDto>> getBusinessReviews(
            @RequestParam(name = "start_idx", defaultValue = "0") int startIdx,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {

       List<BusinessDto> reviews = businessReviewService.getBusinessReview(startIdx, pageSize);
       return ResponseEntity.ok(reviews);
    }

    /**
     * 리뷰 댓글 생성
     */
    @PostMapping("/create")
    public ResponseEntity<String> createReviewReply(@RequestBody ReviewReplyPostDto dto) {
        try {
            Long replyId = businessReviewService.createReviewReply(dto);
            return ResponseEntity.ok("리뷰 댓글이 성공적으로 등록되었습니다. 댓글 ID: " + replyId);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateReviewReply(@RequestBody ReviewReplyUpdateDto dto) {
        try {
            businessReviewService.updateReviewReply(dto);
            return ResponseEntity.ok("리뷰 댓글이 성공적으로 수정되었습니다.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteReviewReply(@RequestParam Long replyId) {
        try {
            businessReviewService.deleteReviewReply(replyId);
            return ResponseEntity.ok("리뷰 댓글이 성공적으로 삭제되었습니다.");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }

}
