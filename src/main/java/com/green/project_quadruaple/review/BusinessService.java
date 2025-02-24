package com.green.project_quadruaple.review;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.review.model.BusinessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService {
    private final BusinessMapper businessMapper;
    private final AuthenticationFacade authenticationFacade; // 로그인 유저 정보 가져오기

    // 전체 비즈니스 리뷰 조회 (로그인 유저 기반)
    public List<BusinessDto> getAllBusinessReviews(int startIdx) {

        // 1. 로그인 유저 정보 가져오기
        Long signedUserId = AuthenticationFacade.getSignedUserId();

        // 2. 사업자 권한 체크
        String userRole = businessMapper.findUserRoleByUserId(signedUserId);
        if (!"BUSINESS".equals(userRole)) {
            throw new AccessDeniedException("사업자 권한이 없습니다.");
        }

        // 3. 전체 리뷰 조회
        return businessMapper.selectAllBusinessReviews(signedUserId, startIdx);
    }
}
