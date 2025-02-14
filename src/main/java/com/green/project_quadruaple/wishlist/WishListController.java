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
    public ResponseEntity<?> getWishList(@RequestParam(value = "start_idx") int startIdx) {
        ResponseWrapper<List<WishListRes>> wishList = wishListService.getWishList(startIdx);
        return ResponseEntity.ok(wishList);
    }
}


