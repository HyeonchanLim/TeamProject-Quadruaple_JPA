package com.green.project_quadruaple.point;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.point.model.dto.PointCardGetDto;
import com.green.project_quadruaple.point.model.dto.PointCardPostDto;
import com.green.project_quadruaple.point.model.payModel.req.PointBuyReadyReq;
import com.green.project_quadruaple.point.model.req.PointHistoryPostReq;
import com.green.project_quadruaple.point.model.res.PointCardProductRes;
import com.green.project_quadruaple.point.model.dto.PointCardUpdateDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("point")
@Tag(name = "포인트")
public class PointController {
    private final PointService pointService;

    @PostMapping("card")
    @Operation(summary = "포인트 상품권 발급")
    public ResponseEntity<?> postPointCard(@RequestBody PointCardPostDto pointCardPostDto) {
        int result = pointService.intPointCard(pointCardPostDto);

        if (result < 0) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @GetMapping("card")
    @Operation(summary = "포인트 상품권 조회, 회원이면 잔여포인트도 같이 전달")
    public ResponseEntity<?> getPointCardProduct() {
        PointCardProductRes result = pointService.getPointCardProduct();

        if (result.getPointCards() == null || result.getPointCards().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @GetMapping("card-admin")
    @Operation(summary = "포인트 상품권 조회/관리자")
    public ResponseEntity<?> getPointCardAdmin() {
        List<PointCardGetDto> result = pointService.getPointCard();

        if (result == null || result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @PatchMapping("card-admin")
    @Operation(summary = "포인트 상품권 수정")
    public ResponseEntity<?> updatePointCard(@RequestBody PointCardUpdateDto pointCardUpdateDto) {
        int result = pointService.updPointCard(pointCardUpdateDto);

        if (result < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @PostMapping
    @Operation(summary = "포인트 사용 혹은 사용취소",
            description = "category가 0이면 사용(relatedId는 상품id), 2면 취소(relatedId는 pointHistoryId), amount는 얼마나 사용했나 혹은 취소되어 들어왔나")
    public ResponseEntity<?> useOrUnUsePoint(@RequestBody PointHistoryPostReq p) {
        return pointService.useOrUnUsePoint(p);
    }

    @GetMapping("history")
    @Operation(summary = "포인트 사용내역 열람",
            description = "category를 null로 보내면 구분없음(0-사용, 1-구매, 2-취소/환불), is_desc가 true면 최신순 false면 오래된 순, page는 몇번째 페이지")
    public ResponseEntity<?> checkMyRemainPoint(@RequestParam("start_at") @Parameter(example = "2025-03-05") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startAt,
                                                @RequestParam("end_at") @Parameter(example = "2025-03-10") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endAt,
                                                @RequestParam(required = false) Integer category, @RequestParam("is_desc") boolean isDesc, int page) {
        return pointService.checkMyRemainPoint(startAt,endAt,category,isDesc,page);
    }

    @PostMapping("card/buy")
    @ResponseBody
    @Operation(summary = "포인트 카드 구매")
    public ResponseWrapper<String> buyingPointCard(@RequestBody PointBuyReadyReq p){
        return pointService.ReadyToBuyPointCard(p);
    }

    @Hidden
    @GetMapping("/pay-approve")
    public String approve(@RequestParam("pg_token") String pgToken){
        return "redirect:" + pointService.approveBuy(pgToken);
    }
}