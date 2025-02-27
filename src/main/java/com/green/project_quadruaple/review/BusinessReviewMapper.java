package com.green.project_quadruaple.review;

import com.green.project_quadruaple.review.model.BusinessDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface BusinessReviewMapper {

    List<BusinessDto> selectBusinessReviews(
            @Param("userId") Long userId,
            @Param("startIdx") int startIdx,
            @Param("pageSize") int pageSize
    );

    // 사용자 역할 조회 (사업자 여부 확인용)
    String findUserRoleByUserId(@Param("userId") Long userId);

}
