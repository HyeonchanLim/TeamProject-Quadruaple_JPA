package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.strf.model.GetNonDetail;
import com.green.project_quadruaple.strf.model.StrfSelRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("detail")
@RequiredArgsConstructor
@Tag(name = "상품")
public class StrfController {
    private final StrfService strfService;

    @GetMapping("member")
    @Operation(summary = "회원 상품 조회")
    public ResponseWrapper<StrfSelRes> getMemberDetail(@RequestParam("strf_id") Long strfId) {

        return strfService.getMemberDetail(strfId);
    }

    @GetMapping("member/non")
    @Operation(summary = "비회원 상품 조회")
    public ResponseWrapper<GetNonDetail> getNonMemberDetail (@RequestParam("strf_id") Long strfId){

        return strfService.getNonMemberDetail(strfId);
    }
}


//    @GetMapping("detail/review")
//    @PostMapping("booking-post")

//    @GetMapping("booking")
//    @GetMapping("booking-list")

//    @Operation(summary = "리뷰 목록 조회", description = "상품 PK를 기반으로 리뷰를 최신순 정렬로 가져옵니다.")
//    @GetMapping("/{strfId}")
//    public ResponseEntity<ReviewResponse> getReviews(
//            @Parameter(description = "상품 PK", example = "1", required = true)
//            @PathVariable Long strfId,
//            @Parameter(description = "최대 리뷰 개수", example = "4", required = false)
//            @RequestParam(defaultValue = "4") int limit
//    ) {
//        ReviewResponse response = reviewService.getReviews(strfId, limit);
//        return ResponseEntity.ok(response);
//    }