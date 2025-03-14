package com.green.project_quadruaple.recent.controller;

import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.recent.service.RecentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("recent")
@Tag(name = "최근 본 목록")
public class RecentController {
    private final RecentService recentService;


    @GetMapping("list")
    @Operation(description = "최근 본 목록 리스트")
    public ResponseWrapper<?> recentList (){
        return recentService.recentList();
    }

    @GetMapping("count")
    @Operation(description = "내가 최근 본 목록 개수")
    public int recentCount (){
        return recentService.recentCount();
    }

    @PatchMapping("hide")
    @Operation(description = "최근 본 목록 개별 비활성화")
    public ResponseWrapper<?> hideRecentItem(@RequestParam("strf_id") Long strfId) {

        return recentService.recentHide(strfId);
    }
    @PatchMapping("hide/all")
    @Operation(description = "최근 본 목록 일괄 비활성화")
    public ResponseWrapper<?> recentAllHide() {

        return recentService.recentAllHide();
    }
}
