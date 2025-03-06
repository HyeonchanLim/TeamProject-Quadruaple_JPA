package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.strf.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
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
    public ResponseWrapper<StrfSelRes> getMemberDetail(@RequestParam("strf_id") Long strfId) {

        return strfService.getMemberDetail(strfId);
    }

    // 상품 조회 api 분류
//    @GetMapping("member")
//    @Operation(summary = "회원 상품 조회")
//    public ResponseWrapper<StrfSelRes> getMemberDetail(@RequestParam("strf_id") Long strfId) {
//
//        return strfService.getMemberDetail(strfId);
//    }
//    @GetMapping("member")
//    @Operation(summary = "회원 상품 조회")
//    public ResponseWrapper<StrfSelRes> getMemberDetail(@RequestParam("strf_id") Long strfId) {
//
//        return strfService.getMemberDetail(strfId);
//    }


    @PostMapping("info")
    @Operation(summary = "사업자 상품 생성")
    public ResponseWrapper<?> strfInfoIns (@RequestPart(required = false) List<MultipartFile> strfPic,
                                         @Valid @RequestPart  StrfInsReq p){
        return strfService.strfInfoIns(strfPic,p);
    }
    @PostMapping("menu")
    @Operation(summary = "사업자 상품 메뉴 생성")
    public ResponseWrapper<?> strfMenuIns (@RequestPart(required = false) List<MultipartFile> menuPic,
                                       @Valid @RequestPart  StrfMenuInsReq p){
        return strfService.strfMenuIns(menuPic,p);
    }
    @PostMapping("stay")
    @Operation(summary = "사업자 상품 객실,숙소 생성")
    public ResponseWrapper<?> strfStayIns (@Valid @RequestPart StrfStayInsReq p){
        return strfService.strfStayIns(p);
    }

    @PutMapping
    public ResponseEntity<?> updateStrf(
            @Param("strf_id") Long strfId,
            @RequestPart(value = "strf_pic", required = false) List<MultipartFile> strfPic,
            @RequestPart(value = "menu_pic", required = false) List<MultipartFile> menuPic,
            @RequestPart @Valid StrfInsReq request,
            @RequestPart @Valid StrfMenuInsReq menuReq,
            @RequestPart @Valid StrfStayInsReq stayReq
            ) {
        ResponseWrapper<Integer> response = strfService.updateStrf(strfId, strfPic, menuPic, request,menuReq,stayReq);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteStrf(@RequestParam("strf_id") Long strfId) {
        ResponseWrapper<Integer> response = strfService.deleteStrf(strfId);
        return ResponseEntity.ok(response);
    }

}

