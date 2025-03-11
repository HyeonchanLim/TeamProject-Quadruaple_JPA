package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.entity.model.Menu;
import com.green.project_quadruaple.entity.model.StayTourRestaurFest;
import com.green.project_quadruaple.strf.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("detail")
@RequiredArgsConstructor
@Tag(name = "상품")
public class StrfController {
    private final StrfService strfService;

    @GetMapping("member")
    @Operation(summary = "상품 조회")
    public ResponseWrapper<StrfSelRes> getMemberDetail(@RequestParam("strf_id") Long strfId ) {

        return strfService.getMemberDetail(strfId);
    }

//     상품 조회 api 분류
    @GetMapping("menu")
    @Operation(summary = "상품 메뉴 조회 ")
    public ResponseWrapper<?> getStrfMenu(@RequestParam("strf_id") Long strfId ) {
        return strfService.getStrfMenu(strfId);
    }
    @GetMapping("amenity")
    @Operation(summary = "상품 편의 조회")
    public ResponseWrapper<?> getStrfAmenity(@RequestParam("strf_id") Long strfId,String category) {
        return strfService.getStrfAmenity(strfId, category);
    }
    @GetMapping("/parlor")
    @Operation(summary = "상품 객실 , 호실 조회")
    public ResponseWrapper<?> getStrfParlor(@RequestParam("strf_id") Long strfId, String category) {
        return strfService.getStrfParlor(strfId, category);
    }


    @PostMapping("/info")
    @Operation(summary = "사업자 상품 생성")
    public ResponseWrapper<?> strfInfoIns (@RequestPart(required = false) List<MultipartFile> strfPic,
                                         @Valid @RequestPart  StrfInsReq p){
        return strfService.strfInfoIns(strfPic,p);
    }

    @PostMapping("/menu")
    @Operation(summary = "사업자 상품 메뉴 생성")
    public ResponseWrapper<?> strfMenuIns (@RequestPart(required = false) List<MultipartFile> menuPic,
                                       @Valid @RequestPart  StrfMenuInsReq p){
        return strfService.strfMenuIns(menuPic,p);
    }

    @PostMapping("/stay")
    @Operation(summary = "사업자 상품 객실,숙소 생성")
    public ResponseWrapper<?> strfStayIns (@Valid @RequestBody StrfStayInsReq p){
        return strfService.strfStayIns(p);
    }

    @PutMapping("/strf")
    public ResponseEntity<?> updateStrf(@Param("strf_id") Long strfId,
                                        @RequestPart @Valid StrfUpdInfo request) {
        ResponseWrapper<Integer> response = strfService.updateStrf(strfId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/state")
    public ResponseEntity<?> updState(@Param("strf_id") Long strfId, int state ,@Param("busi_num") String busiNum) {
        ResponseWrapper<Integer> response = strfService.updState(strfId, state , busiNum);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/detail")
    public ResponseEntity<?> updDetail(@Param("strf_id") Long strfId, String detail ,@Param("busi_num") String busiNum) {
        ResponseWrapper<Integer> response = strfService.updDetail(strfId, detail,busiNum);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/fest/time")
    public ResponseEntity<?> updFestTime(@Param("strf_id") Long strfId,@RequestPart StrfFestTime p) {
        ResponseWrapper<Integer> response = strfService.updFestTime(strfId, p);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/time")
    public ResponseEntity<?> updTime(@Param("strf_id") Long strfId,@RequestPart StrfTime p) {
        ResponseWrapper<Integer> response = strfService.updTime(strfId, p);
        return ResponseEntity.ok(response);
    }

    @PutMapping("tell")
    public ResponseEntity<?> updTell(@Param("strf_id") Long strfId, String tell,@Param("busi_num") String busiNum) {
        ResponseWrapper<Integer> response = strfService.updTell(strfId, tell,busiNum);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/title")
    public ResponseEntity<?> updTitle(@Param("strf_id") Long strfId, String title ,@Param("busi_num") String busiNum) {
        ResponseWrapper<Integer> response = strfService.updTitle(strfId, title,busiNum);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/address")
    public ResponseEntity<?> updAddress(@Param("strf_id") Long strfId,@RequestPart StrfUpdAddress p) {
        ResponseWrapper<Integer> response = strfService.updAddress(strfId , p);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/strf/pic")
    public ResponseEntity<?> updStrfPic(@Param("strf_id") Long strfId,@RequestPart List<MultipartFile> strfPic,@Param("busi_num") String busiNum) {
        ResponseWrapper<Integer> response = strfService.updStrfPic(strfId , strfPic,busiNum);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/rest")
    public ResponseEntity<?> updRest(@Param("strf_id") Long strfId,@RequestPart List<String> restDates,@Param("busi_num") String busiNum) {
        ResponseWrapper<Integer> response = strfService.updRest(strfId , restDates,busiNum);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/menu")
    public ResponseEntity<?> updStrfMenu(@RequestPart List<MultipartFile> menuPic,
                                         @RequestPart StrfMenuInsReq menuReq) {
        ResponseWrapper<Integer> response = strfService.updStrfMenu(menuPic,menuReq);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/stay")
    public ResponseEntity<?> updateStay(@RequestPart StrfStayUpdReq stayReq) {
        ResponseWrapper<Integer> response = strfService.updateStay(stayReq);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/amenity")
    public ResponseEntity<?> updateAmenity(@RequestPart StrfJpaAmenity req) {
        ResponseWrapper<Integer> response = strfService.updateAmenity(req);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteStrf(@RequestParam("strf_id") Long strfId) {
        ResponseWrapper<Integer> response = strfService.deleteStrf(strfId);
        return ResponseEntity.ok(response);
    }

}

