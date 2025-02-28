package com.green.project_quadruaple.review;

import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.entity.model.Review;
import com.green.project_quadruaple.entity.model.ReviewReply;
import com.green.project_quadruaple.entity.model.User;
import com.green.project_quadruaple.review.model.BusinessDto;
import com.green.project_quadruaple.review.model.ReviewPicDto;
import com.green.project_quadruaple.review.model.ReviewReplyReq;
import com.green.project_quadruaple.review.repository.ReviewReplyRepository;
import com.green.project_quadruaple.strf.StrfRepository;
import com.green.project_quadruaple.user.Repository.UserRepository;
import com.green.project_quadruaple.user.model.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessReviewService {
    private final ReviewMapper reviewMapper;
    private final AuthenticationFacade authenticationFacade;
    private final ReviewReplyRepository reviewReplyRepository;
    private final RoleRepository roleRepository;

    private final MyFileUtils myFileUtils;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StrfRepository strfRepository;
    private final ReviewPicRepository reviewPicRepository;



    public List<BusinessDto> getBusinessReview(int startIdx) {
        // 로그인 사용자 ID 가져오기
        Long signedUserId = authenticationFacade.getSignedUserId();
        System.out.println("Signed User ID: " + signedUserId);

        // 사장님 권한 체크
        String userRole = reviewMapper.findUserRoleByUserId(signedUserId);
        if (!"BUSI".equalsIgnoreCase(userRole)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사장님 권한이 없습니다.");
        }

        // 전체 리뷰 조회
        List<BusinessDto> reviews = reviewMapper.selectBusinessReview(signedUserId, startIdx);

        // ✅ 추가: 리뷰마다 reviewPicList 데이터 매핑
        for (BusinessDto review : reviews) {
            List<ReviewPicDto> pics = reviewMapper.selectReviewPics(review.getReviewId());
            review.setReviewPicList(pics);
        }

        return reviews;
    }

    public ResponseWrapper<Long> updateBusiReview(ReviewReplyReq p) {
        Long userId = authenticationFacade.getSignedUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user id not found"));

        Review review = reviewRepository.findById(user.getUserId()).orElseThrow( () -> new RuntimeException("id not found"));

        ReviewReply reviewReply = ReviewReply.builder()
                .replyId(p.getReviewReplyId())
                .content(p.getContent())
                .review(review)
                .build();

        return new ResponseWrapper<>(ResponseCode.OK.getCode(), 1L);
    }



}
