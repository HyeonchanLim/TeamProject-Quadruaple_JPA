package com.green.project_quadruaple.home;

import com.green.project_quadruaple.home.res.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeMapper {
    //home화면 가져오기
    List<RecommendFest> getFestival(long userId);
    List<PopularLocation> getPopularLocation();
    List<RecentStrf> getRecent(long userId);
    List<RecommendStrf> getRecommend(long userId);

    //home화면 without userId
    List<RecommendFest> getFestivalWithOutUserId();
    List<RecommendStrf> getRecommendWithOutUserId();

    //mypage 가져오기
    MyPageRes pushHamburger(long userId);

    //유저가 여행이 있는지 체크
    boolean isUserHaveTrip(long userId);
}
