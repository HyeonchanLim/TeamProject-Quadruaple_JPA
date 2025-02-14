package com.green.project_quadruaple.memo;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.memo.model.MemoDto;
import com.green.project_quadruaple.memo.model.Req.MemoPostReq;
import com.green.project_quadruaple.memo.model.Req.MemoUpReq;
import com.green.project_quadruaple.memo.model.Res.MemoGetRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/memo")
@RequiredArgsConstructor
@Tag(name = "메모")
public class MemoController {

    private final MemoService memoService;
    @GetMapping("/select")
    @Operation(summary = "메모 조회")
    public ResponseEntity<?> selectMemo(@RequestParam Long memoId) {
        return memoService.getMemo(memoId);
    }

    @PostMapping("/post")
    @Operation(summary = "메모 생성")
    public ResponseWrapper<Long> postMemo(@RequestBody MemoPostReq memoDto) {
        return memoService.addMemo(memoDto);

    }

    @PatchMapping("/upd")
    @Operation(summary = "메모 수정")
    public ResponseEntity<?> updateMemo(@RequestBody MemoUpReq memoDto) {
        return memoService.updateMemo(memoDto);


    }

    @DeleteMapping("/delete")
    @Operation(summary = "메모 삭제")
    public ResponseEntity<?> deleteMemo(@RequestParam Long memoId) {
        return memoService.deleteMemo(memoId);

    }






}



