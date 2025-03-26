package com.green.project_quadruaple.review;

import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.entity.model.Review;
import com.green.project_quadruaple.entity.model.ReviewReply;
import com.green.project_quadruaple.entity.repository.ReviewRepository;
import com.green.project_quadruaple.review.model.BusinessDto;
import com.green.project_quadruaple.review.model.ReviewPicDto;
import com.green.project_quadruaple.entity.repository.ReviewReplyRepository;
import com.green.project_quadruaple.review.reviewReply.ReviewReplyPostDto;
import com.green.project_quadruaple.review.reviewReply.ReviewReplyUpdateDto;
import com.green.project_quadruaple.entity.repository.BusinessNumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessReviewService {
    private final ReviewReplyRepository reviewReplyRepository;
    private final BusinessNumRepository businessNumRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;

    //전체 리뷰 조회 (페이징 적용)
    public List<BusinessDto> getBusinessReview(int page, int pageSize) {
        Long signedUserId = authenticationFacade.getSignedUserId();
        System.out.println("Signed User ID: " + signedUserId);

        String userRole = reviewMapper.findUserRoleByUserId(signedUserId);
        if (!"BUSI".equalsIgnoreCase(userRole)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사업자 권한 없음.");
        }

        int startIdx = (page - 1) * pageSize;
        if (startIdx < 0) startIdx = 0;

        System.out.println("startIdx: " + startIdx + ", pageSize: " + pageSize);

        long startTime = System.currentTimeMillis();
        long queryStartTime = System.currentTimeMillis();

        // ⚠️ pageSize + 1개를 조회하여 다음 데이터가 있는지 확인
        List<BusinessDto> reviews = reviewMapper.selectBusinessReview(signedUserId, page, pageSize + 1, startIdx);

        long queryEndTime = System.currentTimeMillis();
        System.out.println("쿼리 실행 시간: " + (queryEndTime - queryStartTime) + "ms");

        // 남은 데이터 개수 확인
        boolean hasMoreData = reviews.size() > pageSize;

        // 리스트에서 최대 pageSize 개수만 유지
        if (hasMoreData) {
            reviews = reviews.subList(0, pageSize);
        }

        // 가져온 데이터 개수와 전체 개수를 비교하여 isMore 설정
        for (int i = 0; i < reviews.size(); i++) {
            if (hasMoreData || i < reviews.size() - 1) {
                reviews.get(i).setIsMore(true);
            } else {
                reviews.get(i).setIsMore(false);
            }
        }

        for (BusinessDto review : reviews) {
            System.out.println("queryReviewReplyId: " + review.getReviewReplyId());

            long reviewStartTime = System.currentTimeMillis();
            List<ReviewPicDto> pics = reviewMapper.selectReviewPics(review.getReviewId());
            long reviewEndTime = System.currentTimeMillis();

            System.out.println("리뷰 ID: " + review.getReviewId() + " 사진 로딩 시간: " + (reviewEndTime - reviewStartTime) + "ms");

            review.setReviewPicList(pics);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("전체 getBusinessReview 실행 시간: " + (endTime - startTime) + "ms");

        return reviews;
    }




    //전체 리뷰 조회 (페이징 적용)
    /*public List<BusinessDto> getBusinessReview(int page, int pageSize) {
        Long signedUserId = authenticationFacade.getSignedUserId();
        System.out.println("Signed User ID: " + signedUserId);

        String userRole = reviewMapper.findUserRoleByUserId(signedUserId);
        if (!"BUSI".equalsIgnoreCase(userRole)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사업자 권한이 없음.");
        }

        int startIdx = (page - 1) * pageSize;
        if (startIdx < 0) startIdx = 0;

        System.out.println("startIdx: " + startIdx + ", pageSize: " + pageSize); // 값 확인용

        // 전체 실행 시간 측정 시작
        long startTime = System.currentTimeMillis();

        // 쿼리 실행 시간 측정
        long queryStartTime = System.currentTimeMillis();
        List<BusinessDto> reviews = reviewMapper.selectBusinessReview(signedUserId, page, pageSize, startIdx);
        long queryEndTime = System.currentTimeMillis();
        System.out.println("쿼리 실행 시간: " + (queryEndTime - queryStartTime) + "ms");

        for (BusinessDto review : reviews) {
            System.out.println("ReviewReplyId: " + review.getReviewReplyId());

            // 개별 리뷰에 대한 실행 시간 측정
            long reviewStartTime = System.currentTimeMillis();
            List<ReviewPicDto> pics = reviewMapper.selectReviewPics(review.getReviewId());
            long reviewEndTime = System.currentTimeMillis();
            System.out.println("리뷰 ID " + review.getReviewId() + " 사진 로딩 시간: " + (reviewEndTime - reviewStartTime) + "ms");

            review.setReviewPicList(pics);
        }

        // 전체 실행 시간 측정 종료
        long endTime = System.currentTimeMillis();
        System.out.println("전체 getBusinessReview 실행 시간: " + (endTime - startTime) + "ms");
        return reviews;
    }*/




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









