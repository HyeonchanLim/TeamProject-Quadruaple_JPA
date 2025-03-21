package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.strf.model.*;
import com.green.project_quadruaple.user.model.UserSignInRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("detail")
@RequiredArgsConstructor
@Tag(name = "상품")
public class StrfController {
    private final StrfService strfService;

    @GetMapping("/check")
    @Operation(summary = "상품 예약 가능 여부 조회")
    public ResponseEntity<?> checkBookingAvailability(
            @RequestParam("strfId") Long strfId,
            @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

        List<StrfCheckRes> availableMenus = strfService.stayBookingExists(strfId, checkIn, checkOut).getData();
        return ResponseEntity.ok(availableMenus);
    }

    @GetMapping("busi/member")
    @Operation(summary = "상품 조회")
    public ResponseWrapper<StrfSelRes> busiMemberDetail(@RequestParam("strf_id") Long strfId) {

        return strfService.busiMemberDetail(strfId);
    }

    @GetMapping("member")
    @Operation(summary = "상품 조회")
    public ResponseWrapper<StrfSelRes> getMemberDetail(@RequestParam("strf_id") Long strfId) {

        return strfService.getMemberDetail(strfId);
    }

    //     상품 조회 api 분류
    @GetMapping("menu")
    @Operation(summary = "상품 메뉴 조회 ")
    public ResponseWrapper<?> getStrfMenu(@RequestParam("strf_id") Long strfId) {
        return strfService.getStrfMenu(strfId);
    }

    @GetMapping("amenity")
    @Operation(summary = "상품 편의 조회")
    public ResponseWrapper<?> getStrfAmenity(@RequestParam("strf_id") Long strfId, String category) {
        return strfService.getStrfAmenity(strfId, category);
    }

    @GetMapping("/parlor")
    @Operation(summary = "상품 객실 , 호실 조회")
    public ResponseWrapper<?> getStrfParlor(@RequestParam("strf_id") Long strfId, String category) {
        return strfService.getStrfParlor(strfId, category);
    }

    @GetMapping("/count")
    @Operation(summary = "리뷰 개수 조회")
    public ResponseEntity<?> reviewCount(@RequestParam("strf_id") long strfId) {

        ResponseWrapper<Integer> reviewCnt = strfService.reviewCount(strfId);
        return ResponseEntity.ok(reviewCnt);
    }

    @GetMapping("/coupon")
    @Operation(summary = "상품이 발행한 쿠폰 조회")
    public List<StrfCouponGetRes> couponList(@RequestParam("strf_id") long strfId) {
        return strfService.couponList(strfId);
    }

    @PostMapping("/info")
    @Operation(summary = "사업자 상품 생성")
    public ResponseWrapper<?> strfInfoIns(@RequestPart(required = false) List<MultipartFile> strfPic,
                                          @Valid @RequestPart StrfInsReq p) {
        return strfService.strfInfoIns(strfPic, p);
    }

    @PostMapping("/menu")
    @Operation(summary = "사업자 상품 메뉴 생성")
    public ResponseWrapper<?> strfMenuIns(@RequestPart(required = false) List<MultipartFile> menuPic,
                                          @Valid @RequestPart StrfMenuInsReq p) {
        return strfService.strfMenuIns(menuPic, p);
    }

    @PostMapping("/stay")
    @Operation(summary = "사업자 상품 객실,숙소 생성")
    public ResponseWrapper<?> strfStayIns(@Valid @RequestBody StrfStayInsReq p) {
        return strfService.strfStayIns(p);
    }

    @PutMapping("/receive")
    @Operation(summary = "쿠폰 수령")
    public ResponseEntity<?> couponReceive(@RequestParam("coupon_id") long couponId) {
        try {
            Integer response = strfService.couponReceive(couponId);

            if (response == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseCode.NOT_FOUND.getCode());
            }

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            String message = e.getMessage();

            if ("이미 수령한 쿠폰입니다.".equals(message)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseCode.ALREADY_RECEIVED_COUPON.getCode());
            } else if ("user id not found".equals(message)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseCode.NOT_FOUND_USER.getCode());
            } else if ("coupon id not found".equals(message)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseCode.NOT_FOUND_COUPON.getCode());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseCode.SERVER_ERROR.getCode());
            }
        }
    }

    @PutMapping("/menu")
    public ResponseEntity<?> updStrfMenu(@RequestPart List<MultipartFile> menuPic,
                                         @RequestPart StrfUpdMenu menuReq) {
        ResponseWrapper<Integer> response = strfService.updStrfMenu(menuPic, menuReq);
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/state")
    public ResponseEntity<?> patchState(@Param("strf_id") Long strfId, int state, @Param("busi_num") String busiNum) {
        ResponseWrapper<Integer> response = strfService.updState(strfId, state, busiNum);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/detail")
    public ResponseEntity<?> patchDetail(@Param("strf_id") Long strfId, String detail, @Param("busi_num") String busiNum) {
        ResponseWrapper<Integer> response = strfService.updDetail(strfId, detail, busiNum);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/fest/time")
    public ResponseEntity<?> patchFestTime(@RequestPart StrfFestTime p) {
        ResponseWrapper<Integer> response = strfService.updFestTime(p);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/time")
    public ResponseEntity<?> patchTime(@RequestBody StrfTime p) {
        ResponseWrapper<Integer> response = strfService.updTime(p);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("tell")
    public ResponseEntity<?> patchTell(@Param("strf_id") Long strfId, String tell, @Param("busi_num") String busiNum) {
        ResponseWrapper<Integer> response = strfService.updTell(strfId, tell, busiNum);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/title")
    public ResponseEntity<?> patchTitle(@Param("strf_id") Long strfId, String title, @Param("busi_num") String busiNum) {
        ResponseWrapper<Integer> response = strfService.updTitle(strfId, title, busiNum);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/address")
    public ResponseEntity<?> patchAddress(@RequestBody StrfUpdAddress p) {
        ResponseWrapper<Integer> response = strfService.updAddress(p);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/strf/pic")
    public ResponseEntity<?> patchStrfPic(@Param("strf_id") Long strfId, @RequestPart List<MultipartFile> strfPic, @Param("busi_num") String busiNum) {
        ResponseWrapper<Integer> response = strfService.updStrfPic(strfId, strfPic, busiNum);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/rest")
    public ResponseEntity<?> patchRest(@RequestParam("strf_id") Long strfId, @RequestBody List<String> restDates, @RequestParam("busi_num") String busiNum) {
        ResponseWrapper<Integer> response = strfService.updRest(strfId, restDates, busiNum);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/menu")
    public ResponseEntity<?> patchStrfMenu(@RequestPart List<MultipartFile> menuPic,
                                           @RequestPart StrfUpdMenu menuReq) {
        ResponseWrapper<Integer> response = strfService.updStrfMenu(menuPic, menuReq);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/stay")
    public ResponseEntity<?> patchStay(@RequestPart StrfStayUpdReq stayReq) {
        ResponseWrapper<Integer> response = strfService.updateStay(stayReq);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/amenity")
    public ResponseEntity<?> patchAmenity(@RequestBody StrfJpaAmenity req) {
        ResponseWrapper<Integer> response = strfService.updateAmenity(req);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/strf")
    @Operation(summary = "상품 삭제")
    public ResponseEntity<?> deleteStrf(@RequestParam("strf_id") Long strfId) {
        ResponseWrapper<Integer> response = strfService.deleteStrf(strfId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/amenity")
    @Operation(summary = "편의 정보 입력 리스트 삭제")
    public ResponseEntity<?> deleteAmenity(Long strfId, String busiNum, List<Long> amenityIds) {
        ResponseWrapper<Integer> response = strfService.deleteAmenity(strfId, busiNum, amenityIds);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/amenity/all")
    @Operation(summary = "상품 편의 정보 전부 삭제")
    public ResponseEntity<?> delAllAmenity (long strfId , String busiNum){
        ResponseWrapper<Integer> response = strfService.delAllAmenity(strfId,busiNum);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/menu")
    @Operation(summary = "상품 메뉴 삭제")
    public ResponseEntity<?> deleteMenu(long menuId, String busiNum) {
        ResponseWrapper<Integer> response = strfService.deleteMenu(menuId, busiNum);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/parlor")
    @Operation(summary = "상품 객실 삭제")
    public ResponseEntity<?> deleteParlor(long menuId, String busiNum) {
        ResponseWrapper<Integer> response = strfService.deleteParlor(menuId, busiNum);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/strf/pic")
    @Operation(summary = "상품 사진 삭제")
    public ResponseEntity<?> deleteStrfPic(String busiNum, String picName) {
        ResponseWrapper<Integer> response = strfService.deleteStrfPic(busiNum, picName);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/rest")
    @Operation(summary = "상품 휴일 삭제 ")
    public ResponseEntity<?> deleteRest(Long strfId, String busiNum) {
        ResponseWrapper<Integer> response = strfService.delAllRest(strfId,busiNum);
        return ResponseEntity.ok(response);
    }

}


//    @PutMapping("/strf")
//    public ResponseEntity<?> updateStrf(@Param("strf_id") Long strfId,
//                                        @RequestPart @Valid StrfUpdInfo request) {
//        ResponseWrapper<Integer> response = strfService.updateStrf(strfId, request);
//        return ResponseEntity.ok(response);
//    }
//    @PutMapping("/state")
//    public ResponseEntity<?> updState(@Param("strf_id") Long strfId, int state ,@Param("busi_num") String busiNum) {
//        ResponseWrapper<Integer> response = strfService.updState(strfId, state , busiNum);
//        return ResponseEntity.ok(response);
//    }
//    @PutMapping("/detail")
//    public ResponseEntity<?> updDetail(@Param("strf_id") Long strfId, String detail ,@Param("busi_num") String busiNum) {
//        ResponseWrapper<Integer> response = strfService.updDetail(strfId, detail,busiNum);
//        return ResponseEntity.ok(response);
//    }
//    @PutMapping("/fest/time")
//    public ResponseEntity<?> updFestTime(@Param("strf_id") Long strfId,@RequestPart StrfFestTime p) {
//        ResponseWrapper<Integer> response = strfService.updFestTime(strfId, p);
//        return ResponseEntity.ok(response);
//    }
//    @PutMapping("/time")
//    public ResponseEntity<?> updTime(@RequestBody StrfTime p) {
//        ResponseWrapper<Integer> response = strfService.updTime(p);
//        return ResponseEntity.ok(response);
//    }
//    @PutMapping("tell")
//    public ResponseEntity<?> updTell(@Param("strf_id") Long strfId, String tell,@Param("busi_num") String busiNum) {
//        ResponseWrapper<Integer> response = strfService.updTell(strfId, tell,busiNum);
//        return ResponseEntity.ok(response);
//    }
//    @PutMapping("/title")
//    public ResponseEntity<?> updTitle(@Param("strf_id") Long strfId, String title ,@Param("busi_num") String busiNum) {
//        ResponseWrapper<Integer> response = strfService.updTitle(strfId, title,busiNum);
//        return ResponseEntity.ok(response);
//    }
//    @PutMapping("/address")
//    public ResponseEntity<?> updAddress(@Param("strf_id") Long strfId,@RequestPart StrfUpdAddress p) {
//        ResponseWrapper<Integer> response = strfService.updAddress(strfId , p);
//        return ResponseEntity.ok(response);
//    }
////    @PutMapping("/strf/pic")
////    public ResponseEntity<?> updStrfPic(@Param("strf_id") Long strfId,@RequestPart List<MultipartFile> strfPic,@Param("busi_num") String busiNum) {
////        ResponseWrapper<Integer> response = strfService.updStrfPic(strfId , strfPic,busiNum);
////        return ResponseEntity.ok(response);
////    }
//    @PutMapping("/rest")
//    public ResponseEntity<?> updRest(@RequestParam("strf_id") Long strfId,@RequestBody List<String> restDates,@RequestParam("busi_num") String busiNum) {
//        ResponseWrapper<Integer> response = strfService.updRest(strfId , restDates,busiNum);
//        return ResponseEntity.ok(response);
//    }






//@DeleteMapping("/room")
//public ResponseEntity<?> deleteRoom(long roomId, String busiNum){
//    ResponseWrapper<Integer> response = strfService.deleteRoom(roomId,busiNum);
//    return ResponseEntity.ok(response);
//}

