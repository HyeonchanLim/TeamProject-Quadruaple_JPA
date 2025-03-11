package com.green.project_quadruaple.busi;

import com.green.project_quadruaple.busi.model.BusiPostReq;
import com.green.project_quadruaple.busi.model.BusiUserInfoDto;
import com.green.project_quadruaple.busi.model.BusiUserSignIn;
import com.green.project_quadruaple.busi.model.BusiUserSignInRes;
import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.user.model.UserSignInReq;
import com.green.project_quadruaple.user.model.UserSignInRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("busi")
@Tag(name = "사업자")
public class BusiController {
    private final BusiService busiService;


    // 회원가입 요청
    @PostMapping("/sign-up")
    @Operation(summary = "사업자 회원가입", description = "사진 파일 때문에 postman 사용")
    public ResponseEntity<?> signUpUser(@RequestPart(required = false) MultipartFile profilePic, @Valid @RequestPart BusiPostReq p) {

        int result = busiService.busiSignUp(profilePic, p);

        if (result < 0) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    //프로필 및 계정 조회
    @GetMapping("/userInfo")
    @Operation(summary = "프로필 및 계정 조회")
    public ResponseEntity<?> BusiUserInfo() {
        BusiUserInfoDto userInfo = busiService.BusiUserInfo();

        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), userInfo));
    }

    //로그인
    @PostMapping("/sign-in")
    @Operation(summary = "로그인")
    public ResponseEntity<?> signInUser(@RequestBody BusiUserSignIn req, HttpServletResponse response) {
        BusiUserSignInRes res = busiService.signIn(req, response);
        if (res == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseCode.NOT_FOUND.getCode());
        }

        // 로그인 성공 시 반환할 데이터 생성
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("code", ResponseCode.OK.getCode());
        responseBody.put("userId", res.getUserId());
        responseBody.put("accessToken", res.getAccessToken());
        responseBody.put("role", res.getRoles());
        responseBody.put("strfDtos" , res.getDtos());

        return ResponseEntity.ok(responseBody);
    }
}
