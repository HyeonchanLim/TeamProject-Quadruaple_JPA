package com.green.project_quadruaple.wishlist;

import com.green.project_quadruaple.wishlist.model.wishlistDto.WishListRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface WishListMapper {

    boolean isWishListExists(Long userId, @Param("strfId") long strfId);
    void deleteWishList(Long userId, @Param("strfId") long strfId);

    void insertWishList(Long userId, long strfId);

    List<WishListRes> wishListGet(Long userId, int startIdx , int size);



    // 위시리스트 조회 쿼리
    /*List<WishListRes> findWishList(@Param("userId") Long userId,
                                   @Param("categoryList") List<String> categoryList,
                                   @Param("offset") int offset,
                                   @Param("pageSize") int pageSize);*/
}
