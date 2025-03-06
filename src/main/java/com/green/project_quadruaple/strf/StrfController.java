package com.green.project_quadruaple.strf;

import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.review.model.ReviewPostJpaReq;
import com.green.project_quadruaple.strf.model.GetNonDetail;
import com.green.project_quadruaple.strf.model.StrfInsReq;
import com.green.project_quadruaple.strf.model.StrfInsRes;
import com.green.project_quadruaple.strf.model.StrfSelRes;
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
    @Operation(summary = "회원 상품 조회")
    public ResponseWrapper<StrfSelRes> getMemberDetail(@RequestParam("strf_id") Long strfId) {

        return strfService.getMemberDetail(strfId);
    }

    @GetMapping("member/non")
    @Operation(summary = "비회원 상품 조회")
    public ResponseWrapper<GetNonDetail> getNonMemberDetail (@RequestParam("strf_id") Long strfId){

        return strfService.getNonMemberDetail(strfId);
    }

    @PostMapping
    @Operation(summary = "사업자 상품 생성")
    public ResponseWrapper<?> strfIns (@RequestPart(required = false) List<MultipartFile> strfPic,
                                         @RequestPart(required = false) List<MultipartFile> menuPic,
                                         @Valid @RequestPart  StrfInsReq p){
        return strfService.strfIns(strfPic,menuPic,p);
    }

    @PutMapping
    public ResponseEntity<?> updateStrf(
            @Param("strf_id") Long strfId,
            @RequestPart(value = "strfPic", required = false) List<MultipartFile> strfPic,
            @RequestPart(value = "menuPic", required = false) List<MultipartFile> menuPic,
            @RequestPart("request") @Valid StrfInsReq request
    ) {
        ResponseWrapper<Integer> response = strfService.updateStrf(strfId, strfPic, menuPic, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteStrf(@PathVariable("strf_id") Long strfId) {
        ResponseWrapper<Integer> response = strfService.deleteStrf(strfId);
        return ResponseEntity.ok(response);
    }

}

