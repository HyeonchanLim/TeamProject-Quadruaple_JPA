package com.green.project_quadruaple.recent;

import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.recent.model.RecentGetListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PatchMapping("hide")
    @Operation(description = "최근 본 목록 개별 비활성화")
    public ResponseEntity<?> hideRecentItem(@RequestParam("strf_id") Long strfId) {
        int updatedCount = recentService.recentHide(strfId);
        return ResponseEntity.ok(strfId);
    }
    @PatchMapping("hide/all")
    @Operation(description = "최근 본 목록 일괄 비활성화")
    public ResponseEntity<?> recentAllHide() {
        int updatedCount = recentService.recentAllHide();
        return ResponseEntity.ok(updatedCount);
    }
}
