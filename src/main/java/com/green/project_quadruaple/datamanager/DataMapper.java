package com.green.project_quadruaple.datamanager;

import com.green.project_quadruaple.datamanager.model.ReviewRandomReq;
import com.green.project_quadruaple.datamanager.model.StrfIdGetReq;
import com.green.project_quadruaple.datamanager.model.StrfReviewGetReq;
import com.green.project_quadruaple.datamanager.model.UserProfile;
import com.green.project_quadruaple.entity.model.Room;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface DataMapper {
    //반복문 돌릴 리뷰 남기기
    void postRating(ReviewRandomReq item);
    //반복문 돌려 리뷰 사진 남기기
    int postReviewPicList(List<Map<String,Object>> ReviewPics);

    List<Long> selectReviewStrfId(StrfReviewGetReq p);
    //strf_id 목록 찾기
    List<Long> selectStrfId(StrfIdGetReq p);
    //strf 테이블에 pic넣기
    int insStrfPic(List<Map<String, Object>> picAndStrfIds);
    //strf pic 삭제
    int delStrfIdPic(List<Long> strfIds);

    //menu 추가
    int insMenu(List<Map<String, Object>> menuData);
    //menu 삭제
    int delMenu(List<Long> strfIds);


    //user 전체 profile pic 찾기
    List<UserProfile> getAllUsersProfilePics();
    //user profile pic 바꾸기
    int updateProfilePicsToDefault(List<Long> userIds, String defaultPic);
}
