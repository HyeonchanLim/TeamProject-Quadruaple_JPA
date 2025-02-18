package com.green.project_quadruaple.wishlist;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.trip.model.Category;
import com.green.project_quadruaple.wishlist.model.WishlistFestRes;
import com.green.project_quadruaple.wishlist.model.wishlistDto.WishListRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final AuthenticationFacade authenticationFacade;
    private final WishListMapper wishlistMapper;


    @Value("${const.default-review-size}")
    private int size;
    private String category;
    private String startAt;
    private String endAt;

    public ResponseEntity<ResponseWrapper<String>> toggleWishList(long strfId) {
        Long userId = authenticationFacade.getSignedUserId();

        // 찜 상태 확인
        boolean isWishListed = wishlistMapper.isWishListExists(userId, strfId);

        if (isWishListed) {
            // 찜 삭제
            wishlistMapper.deleteWishList(userId, strfId);
            return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), "찜이 삭제되었습니다."));
        } else {
            // 찜 추가
            wishlistMapper.insertWishList(userId, strfId);
            return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), "찜이 추가되었습니다."));
        }
    }

//    public ResponseWrapper<Map<String, Object>> getWishList(List<String> categoryList, int page) {
//        int limit = 10;
//        int offset = (page - 1) * limit;
//
//        List<Map<String, Object>> wishList = wishlistMapper.getWishList(categoryList, offset, limit);
//        return new ResponseWrapper<>(ResponseCode.OK.getCode(), Map.of("wishList", wishList));
//
//
//    }

    public ResponseWrapper<List<WishListRes>> getWishList(int startIdx, String orderType,String category) {
        Long userId = authenticationFacade.getSignedUserId();

        int more = 1;
        if (category == null || !category.equals("FEST")) {
            startAt = null;
            endAt = null;
        }
        String categoryValue = null;
        if(category != null && Category.getKeyByName(category) != null) {
            categoryValue = Objects.requireNonNull(Category.getKeyByName(category)).getValue();
        }

        List<WishListRes> list = wishlistMapper.wishListGet(userId, startIdx, size + more, orderType, categoryValue);
        list.forEach(stay -> {
            if (stay.getRatingAvg() != null) {
                double roundedRating = Math.round(stay.getRatingAvg() * 10) / 10.0;
                stay.setRatingAvg(roundedRating);
            }
        });

        boolean hasMore = list.size() > size;

        if (hasMore) {
            list.get(list.size() - 1).setMore(true);
            list.remove(list.size() - 1);
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), list);
    }

    public ResponseWrapper<List<WishlistFestRes>> wishlistFestGet (int startIdx , String orderType , String category , String startAt){
        Long userId = authenticationFacade.getSignedUserId();

        int more = 1;
        if (startAt == null) {
            startAt = null;
        }
        String categoryValue = null;
        if(category != null && Category.getKeyByName(category) != null) {
            categoryValue = Objects.requireNonNull(Category.getKeyByName(category)).getValue();
        }

        List<WishlistFestRes> list = wishlistMapper.wishlistFestGet(userId, startIdx, size + more, orderType, categoryValue ,startAt);
        list.forEach(stay -> {
            if (stay.getRatingAVG() != null) {
                double roundedRating = Math.round(stay.getRatingAVG() * 10) / 10.0;
                stay.setRatingAVG(roundedRating);
            }
        });
        boolean hasMore = list.size() > size;

        if (hasMore) {
            list.get(list.size() - 1).setMore(true);
            list.remove(list.size() - 1);
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), list);
    }
}





