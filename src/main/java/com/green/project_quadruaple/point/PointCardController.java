package com.green.project_quadruaple.point;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.point.model.dto.PointCardGetDto;
import com.green.project_quadruaple.point.model.dto.PointCardPostDto;
import com.green.project_quadruaple.point.model.res.PointCardProductRes;
import com.green.project_quadruaple.point.model.dto.PointCardUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("point-card")
@Tag(name = "포인트 상품권")
public class PointCardController {
    private final PointCardService pointCardService;

    @PostMapping
    @Operation(summary = "포인트 상품권 발급")
    public ResponseEntity<?> postPointCard(@RequestBody PointCardPostDto pointCardPostDto) {
        int result = pointCardService.intPointCard(pointCardPostDto);

        if (result < 0) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @GetMapping("product")
    @Operation(summary = "포인트 상품권 조회, 회원이면 잔여포인트도 같이 전달")
    public ResponseEntity<?> getPointCardProduct() {
        PointCardProductRes result = pointCardService.getPointCardProduct();

        if (result.getPointCards() == null || result.getPointCards().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @GetMapping("admin")
    @Operation(summary = "포인트 상품권 조회/관리자")
    public ResponseEntity<?> getPointCardAdmin() {
        List<PointCardGetDto> result = pointCardService.getPointCard();

        if (result == null || result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @PatchMapping()
    @Operation(summary = "포인트 상품권 수정")
    public ResponseEntity<?> updatePointCard(@RequestBody PointCardUpdateDto pointCardUpdateDto) {
        int result = pointCardService.updPointCard(pointCardUpdateDto);

        if (result < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }
}
