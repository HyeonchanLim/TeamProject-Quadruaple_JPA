package com.green.project_quadruaple.point;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.point.model.dto.PointCardGetDto;
import com.green.project_quadruaple.point.model.dto.PointCardPostDto;
import com.green.project_quadruaple.point.model.payModel.req.PointBuyReadyReq;
import com.green.project_quadruaple.point.model.req.CancelPointUsed;
import com.green.project_quadruaple.point.model.req.PointHistoryPostReq;
import com.green.project_quadruaple.point.model.res.PointCardProductRes;
import com.green.project_quadruaple.point.model.dto.PointCardUpdateDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("point")
@Tag(name = "포인트")
public class PointController {
    private final PointService pointService;

    @Value("${const.point-qr-code-url}")
    private String QR_CODE_URL;

    @GetMapping("QRcode")
    @ResponseBody
    @Operation(summary = "포인트 사용 QRcode생성")
    public ResponseEntity<byte[]> qrToTistory(@RequestParam("strf_id") String strfId
            , @RequestParam int amount) throws WriterException, IOException {
        // QR 정보
        int width = 200;
        int height = 200;
        String url = QR_CODE_URL+strfId+"&amount="+amount;

        // QR Code - BitMatrix: qr code 정보 생성
        BitMatrix encode = new MultiFormatWriter()
                .encode(url, BarcodeFormat.QR_CODE, width, height);

        try {
            //output Stream
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            //Bitmatrix, file.format, outputStream
            MatrixToImageWriter.writeToStream(encode, "PNG", out);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(out.toByteArray());

        }catch (Exception e){log.warn("QR Code OutputStream 도중 Excpetion 발생, {}", e.getMessage());}

        return null;
    }

    @PostMapping("card")
    @ResponseBody
    @Operation(summary = "포인트 상품권 발급")
    public ResponseEntity<?> postPointCard(@RequestBody PointCardPostDto pointCardPostDto) {
        int result = pointService.intPointCard(pointCardPostDto);

        if (result < 0) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @GetMapping("card")
    @ResponseBody
    @Operation(summary = "포인트 상품권 조회, 회원이면 잔여포인트도 같이 전달")
    public ResponseEntity<?> getPointCardProduct() {
        PointCardProductRes result = pointService.getPointCardProduct();

        if (result.getPointCards() == null || result.getPointCards().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @GetMapping("card-admin")
    @ResponseBody
    @Operation(summary = "포인트 상품권 조회/관리자")
    public ResponseEntity<?> getPointCardAdmin() {
        List<PointCardGetDto> result = pointService.getPointCard();

        if (result == null || result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @PatchMapping("card-admin")
    @ResponseBody
    @Operation(summary = "포인트 상품권 수정")
    public ResponseEntity<?> updatePointCard(@RequestBody PointCardUpdateDto pointCardUpdateDto) {
        int result = pointService.updPointCard(pointCardUpdateDto);

        if (result < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @GetMapping("use")
    @ResponseBody
    @Operation(summary = "QR코드 인증시 보일 화면")
    public ResponseEntity<?> QRscanned(@RequestParam("strf_id") long strfId, @RequestParam int amount) {
        return pointService.QRscanned(strfId, amount);
    }

    @PostMapping("history")
    @ResponseBody
    @Operation(summary = "포인트 사용",
            description = "strf_id는 상품id, amount는 얼마나 사용했나")
    public ResponseEntity<?> usePoint(@RequestBody PointHistoryPostReq p) {
        return pointService.usePoint(p);
    }
    @DeleteMapping("history")
    @ResponseBody
    @Operation(summary = "포인트 사용 취소",
            description = "strf_id는 상품id, 결제된 포인트 사용 list 조회의 point_history_id 보내주면 됨.")
    public ResponseEntity<?> cancelUsedPoint(@RequestBody CancelPointUsed p) {
        return pointService.cancelUsedPoint(p);
    }

    @GetMapping("history")
    @ResponseBody
    @Operation(summary = "포인트 사용내역 열람",
            description = "category를 null로 보내면 구분없음(0-사용, 1-구매, 2-환불, 4-사용취소), is_desc가 true면 최신순 false면 오래된 순, page는 몇번째 페이지")
    public ResponseEntity<?> checkMyRemainPoint(@RequestParam("start_at") @Parameter(example = "2025-03-05") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startAt,
                                                @RequestParam("end_at") @Parameter(example = "2025-03-10") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endAt,
                                                @RequestParam(required = false) Integer category, @RequestParam("is_desc") boolean isDesc) {
        return pointService.checkMyRemainPoint(startAt,endAt,category,isDesc);
    }

    @GetMapping("remain-point")
    @ResponseBody
    @Operation(summary = "잔여포인트확인")
    public ResponseEntity<?> getRemainPoint() {
        return pointService.getMyRemainPoint();
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

    @GetMapping
    @ResponseBody
    @Operation(summary = "환불가능 포인트 리스트 보기")
    public ResponseEntity<?> refundableList(){
        return pointService.refundableList();
    }

    @PostMapping("card/refund")
    @ResponseBody
    @Operation(summary = "포인트 환불")
    public ResponseWrapper<String> refundPoint(@RequestParam long pointHistoryId) {
        return pointService.refundPoint(pointHistoryId);
    }
}