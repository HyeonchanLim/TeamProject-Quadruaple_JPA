package com.green.project_quadruaple.review;

import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.review.model.BusinessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessReviewService {
    private final ReviewMapper reviewMapper;  // ✅ 수정: BusinessReviewMapper -> ReviewMapper
    private final AuthenticationFacade authenticationFacade;

    public List<BusinessDto> getBusinessReviews(int startIdx, int pageSize) {
        // 로그인한 유저 ID 가져오기
        Long signedUserId = authenticationFacade.getSignedUserId();

        // ✅ 사업자 권한 체크
        String userRole = reviewMapper.findUserRoleByUserId(signedUserId);
        if (!"BUSI".equalsIgnoreCase(userRole)) {
            throw new SecurityException("사업자 권한이 없습니다.");
        }


        // ✅ 전체 리뷰 조회
        return reviewMapper.selectBusinessReviews(signedUserId, startIdx, pageSize);
    }
}
