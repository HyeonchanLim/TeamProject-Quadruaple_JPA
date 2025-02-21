package com.green.project_quadruaple.wishlist;

import com.green.project_quadruaple.wishlist.model.WishlistFestRes;
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

    List<WishListRes> wishListGet(Long userId, int startIdx , int size, String orderType,String category);

    List<WishlistFestRes> wishlistFestGet(Long userId , int startIdx , int size , String orderType , String category , String startAt);
}
