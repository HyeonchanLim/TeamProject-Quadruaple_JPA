package com.green.project_quadruaple.review;

import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.entity.model.ReviewReply;
import com.green.project_quadruaple.review.model.BusinessDto;
import com.green.project_quadruaple.review.repository.ReviewReplyRepository;
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



    public List<BusinessDto> getBusinessReview(int startIdx) {
        // 로그인된 사용자 ID 가져오기
        Long signedUserId = authenticationFacade.getSignedUserId();
        System.out.println("Signed User ID: " + signedUserId);

        // 사업자 권한 체크
        String userRole = reviewMapper.findUserRoleByUserId(signedUserId);
        System.out.println("Fetched userRole: " + userRole);

        if (!"BUSI".equalsIgnoreCase(userRole)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사업자 권한이 없습니다.");
        }

        // 전체 리뷰 조회
        List<BusinessDto> reviews = reviewMapper.selectBusinessReview(signedUserId, startIdx);
        System.out.println("Fetched Reviews: " + reviews);

        return reviews;
    }




}
