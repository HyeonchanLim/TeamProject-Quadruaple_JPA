package com.green.project_quadruaple.review;

import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.entity.model.Review;
import com.green.project_quadruaple.entity.model.ReviewReply;
import com.green.project_quadruaple.review.model.BusinessDto;
import com.green.project_quadruaple.review.model.ReviewPicDto;
import com.green.project_quadruaple.review.repository.ReviewReplyRepository;
import com.green.project_quadruaple.review.reviewReply.ReviewReplyPostDto;
import com.green.project_quadruaple.review.reviewReply.ReviewReplyUpdateDto;
import com.green.project_quadruaple.strf.BusinessNumRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.green.project_quadruaple.entity.model.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessReviewService {
    private final ReviewReplyRepository reviewReplyRepository;
    private final BusinessNumRepository businessNumRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;

    /**
     * 전체 리뷰 조회 (페이징 적용)
     */
    public List<BusinessDto> getBusinessReview(int page, int pageSize) {
        Long signedUserId = authenticationFacade.getSignedUserId();
        System.out.println("Signed User ID: " + signedUserId);

        String userRole = reviewMapper.findUserRoleByUserId(signedUserId);
        if (!"BUSI".equalsIgnoreCase(userRole)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사업자 권한이 없음.");
        }

        int offset = (page - 1) * pageSize;
        List<BusinessDto> reviews = reviewMapper.selectBusinessReview(signedUserId, page, pageSize, offset);

        for (BusinessDto review : reviews) {
            System.out.println("ReviewReplyId: " + review.getReviewReplyId()); // ✅ 로그 추가
            List<ReviewPicDto> pics = reviewMapper.selectReviewPics(review.getReviewId());
            review.setReviewPicList(pics);
        }

        return reviews;
    }



    @Transactional
    public Long createReviewReply(ReviewReplyPostDto requestDto) {
        Long userId = authenticationFacade.getSignedUserId(); // 로그인한 사용자 ID

        // 로그인한 사용자의 사업자 번호 조회
        List<String> businessNums = businessNumRepository.findBusiNumByUserId(userId);
        if (businessNums.isEmpty()) {
            throw new AccessDeniedException("사업자 정보가 존재하지 않습니다.");
        }

        // 리뷰 조회
        Review review = reviewRepository.findById(requestDto.getReviewId())
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        // 리뷰가 속한 사업장의 strfId 조회
        Long strfId = review.getStayTourRestaurFest().getStrfId();

        // 해당 리뷰가 속한 사업자 번호 조회
        String reviewBusiNum = businessNumRepository.findBusiNumByStrfId(strfId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 속한 사업자 정보가 없습니다."));

        // 로그인한 사용자의 사업자 번호와 비교하여 권한 체크
        if (!businessNums.contains(reviewBusiNum)) {
            throw new AccessDeniedException("해당 리뷰 대댓글을 작성할 권한이 없습니다.");
        }

        // 대댓글 저장
        ReviewReply reviewReply = ReviewReply.builder()
                .review(review)
                .content(requestDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        reviewReplyRepository.save(reviewReply);
        return reviewReply.getReplyId();
    }





    @Transactional
    public void updateReviewReply(ReviewReplyUpdateDto requestDto) {
        Long userId = authenticationFacade.getSignedUserId();

        // 로그인한 사용자의 사업자 번호 조회
        List<String> businessNums = businessNumRepository.findBusiNumByUserId(userId);
        if (businessNums.isEmpty()) {
            throw new AccessDeniedException("사업자 정보가 존재하지 않습니다.");
        }

        // 수정할 대댓글 조회
        ReviewReply reviewReply = reviewReplyRepository.findById(requestDto.getReviewReplyId())
                .orElseThrow(() -> new IllegalArgumentException("해당 대댓글이 존재하지 않습니다."));

        // 대댓글이 속한 리뷰 조회
        Review review = reviewReply.getReview();

        // 리뷰가 속한 사업장의 strfId 조회
        Long strfId = review.getStayTourRestaurFest().getStrfId();

        // 해당 리뷰가 속한 사업자 번호 조회
        String reviewBusiNum = businessNumRepository.findBusiNumByStrfId(strfId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 속한 사업자 정보가 없습니다."));

        // 로그인한 사용자의 사업자 번호와 비교하여 권한 체크
        if (!businessNums.contains(reviewBusiNum)) {
            throw new AccessDeniedException("해당 리뷰 대댓글을 수정할 권한이 없습니다.");
        }

        // 대댓글 내용 수정
        reviewReply.setContent(requestDto.getContent());
        reviewReply.setUpdatedAt(LocalDateTime.now());

        // 리뷰의 updatedAt 필드 업데이트
        review.setUpdatedAt(LocalDateTime.now());

        reviewReplyRepository.save(reviewReply);
        reviewRepository.save(review);
    }




    @Transactional
    public void deleteReviewReply(Long reviewReplyId) {
        Long userId = authenticationFacade.getSignedUserId();

        // 로그인한 사용자의 사업자 번호 조회
        List<String> businessNums = businessNumRepository.findBusiNumByUserId(userId);
        if (businessNums.isEmpty()) {
            throw new AccessDeniedException("사업자 정보가 존재하지 않습니다.");
        }

        // 삭제할 대댓글 조회
        ReviewReply reviewReply = reviewReplyRepository.findById(reviewReplyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 대댓글이 존재하지 않습니다."));

        // 대댓글이 속한 리뷰 조회
        Review review = reviewReply.getReview();

        // 리뷰가 속한 사업장의 strfId 조회
        Long strfId = review.getStayTourRestaurFest().getStrfId();

        // 해당 리뷰가 속한 사업자 번호 조회
        String reviewBusiNum = businessNumRepository.findBusiNumByStrfId(strfId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 속한 사업자 정보가 없습니다."));

        // 로그인한 사용자의 사업자 번호와 비교하여 권한 체크
        if (!businessNums.contains(reviewBusiNum)) {
            throw new AccessDeniedException("해당 리뷰 대댓글을 삭제할 권한이 없습니다.");
        }

        // 대댓글 삭제
        reviewReplyRepository.delete(reviewReply);
    }





}









