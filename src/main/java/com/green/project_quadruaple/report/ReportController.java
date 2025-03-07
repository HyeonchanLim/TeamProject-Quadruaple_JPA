package com.green.project_quadruaple.report;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.report.model.ReportGetByAmdinDto;
import com.green.project_quadruaple.report.model.ReportPostByUserDto;
import com.green.project_quadruaple.report.model.ReportUpdateByAdminDto;
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
@RequestMapping("report")
@Tag(name = "신고")
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    @Operation(summary = "신고 추가")
    public ResponseEntity<?> insReport(@RequestBody ReportPostByUserDto dto) {
        int result = reportService.insReport(dto);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0));
        }

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @PatchMapping
    @Operation(summary = "신고 수정")
    public ResponseEntity<?> updReport(@RequestBody ReportUpdateByAdminDto dto) {
        int result = reportService.updReport(dto);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), 0));
        }

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    @GetMapping
    @Operation(summary = "신고 내역 조회")
    public ResponseEntity<?> getReport() {
        List<ReportGetByAmdinDto> result = reportService.getReportByAmdinDto();

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }
}
