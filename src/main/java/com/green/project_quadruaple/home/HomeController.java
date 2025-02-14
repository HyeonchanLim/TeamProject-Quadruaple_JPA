package com.green.project_quadruaple.home;

import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.home.res.HomeRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("home")
@Tag(name = "홈화면")
public class HomeController {
    private final HomeService homeService;

    //홈화면
    @GetMapping
    @Operation(summary = "홈화면 부르기", description = "추천축제,인기지역,최근본목록(축제제외)")
    public ResponseEntity<?> getHome(){
        return homeService.getHome();
    }


    //마이페이지
    @GetMapping("user")
    @Operation(summary = "마이페이지", description = "홈에서 햄버거버튼 누르면 나오는 그거.")
    public ResponseEntity<?> myPage(){
        return homeService.myPage();
    }
}
