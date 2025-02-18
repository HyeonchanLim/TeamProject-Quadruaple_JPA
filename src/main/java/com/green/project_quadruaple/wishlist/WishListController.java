package com.green.project_quadruaple.wishlist;

import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.wishlist.model.wishlistDto.WishListReq;
import com.green.project_quadruaple.wishlist.model.wishlistDto.WishListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("wish-list")
@Tag(name = "찜")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;


    @PostMapping
    @Operation(summary = "찜 추가, 삭제")
    public ResponseEntity<ResponseWrapper<String>> toggleWishList(@RequestBody WishListReq wishListReq) {
        // 서비스 호출 후 결과 반환
        return wishListService.toggleWishList(wishListReq.getStrfId());
    }

    @GetMapping
    @Operation(summary = "찜 목록")
    public ResponseEntity<?> getWishList(@RequestParam(value = "start_idx") int startIdx,
                                         @RequestParam(value = "orderType", required = false) String orderType,
                                         @RequestParam(value = "startAt", required = false) String startAt,
                                         @RequestParam(value = "endAt", required = false) String endAt
    ) {
        ResponseWrapper<List<WishListRes>> wishList = wishListService.getWishList(startIdx, orderType);
        return ResponseEntity.ok(wishList);
    }
}


