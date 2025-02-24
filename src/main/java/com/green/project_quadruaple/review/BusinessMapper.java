package com.green.project_quadruaple.review;

import com.green.project_quadruaple.review.model.BusinessDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface BusinessMapper {

    // 전체 비즈니스 리뷰 조회
    List<BusinessDto> selectAllBusinessReviews(@Param("userId") Long userId,
                                               @Param("startIdx") int startIdx);

    // 사용자 역할 조회 (유저 권한 체크용)
    String findUserRoleByUserId(@Param("userId") Long userId);
}
