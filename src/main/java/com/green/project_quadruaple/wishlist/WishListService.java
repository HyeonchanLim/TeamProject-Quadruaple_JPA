package com.green.project_quadruaple.wishlist;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.wishlist.model.wishlistDto.WishListRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ResponseWrapper<List<WishListRes>> getWishList(int startIdx, String orderType) {
        Long userId = authenticationFacade.getSignedUserId();

        int more = 1;
        if (category == null || !category.equals("FEST")) {
            startAt = null;
            endAt = null;
        }

        List<WishListRes> list = wishlistMapper.wishListGet(userId, startIdx, size + more, orderType, category, startAt, endAt);

        boolean hasMore = list.size() > size;

        if (hasMore) {
            list.get(list.size() - 1).setMore(true);
            list.remove(list.size() - 1);
        }
        return new ResponseWrapper<>(ResponseCode.OK.getCode(), list);
    }
}





