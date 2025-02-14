package com.green.project_quadruaple.user;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.config.jwt.TokenProvider;
import com.green.project_quadruaple.common.config.jwt.JwtUser;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.common.model.ResultResponse;
import com.green.project_quadruaple.user.model.*;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("user")
@Tag(name = "사용자")
public class UserController {
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    // 회원가입 요청
    @PostMapping("sign-up")
    @Operation(summary = "회원가입", description = "사진 파일 때문에 postman 사용")
    public ResponseEntity<?> signUpUser(@RequestPart(required = false) MultipartFile profilePic, @Valid @RequestPart UserSignUpReq p) {
        int result = userService.signUp(profilePic, p);

        if (result < 0) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    //아이디 중복 체크
    @GetMapping("sign-up")
    @Operation(summary = "아이디 중복 체크", description = "false면 중복 있음, true면 중복 없음")
    public ResponseEntity<?> checkDuplicatedEmail(@RequestParam String email) {
        boolean result = userService.checkDuplicatedEmail(email);
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    //로그인
    @PostMapping("sign-in")
    @Operation(summary = "로그인")
    public ResponseEntity<?> signInUser(@RequestBody UserSignInReq req, HttpServletResponse response) {
        UserSignInRes res = userService.signIn(req, response);
        if (res == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseCode.NOT_FOUND.getCode());
        }

        // 로그인 성공 시 반환할 데이터 생성
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("code", ResponseCode.OK.getCode());
        responseBody.put("data", 200);  // 혹은 적절한 성공 데이터를 설정
        responseBody.put("userId", res.getUserId());
        responseBody.put("accessToken", res.getAccessToken());

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("access-token")
    @Operation(summary = "accessToken 재발행")
    public String getAccessToken(HttpServletRequest req) {
        return userService.getAccessToken(req);
    }

    //프로필 및 계정 조회
    @GetMapping("userInfo")
    @Operation(summary = "프로필 및 계정 조회")
    public ResponseEntity<ResponseWrapper<UserInfoDto>> getUserInfo() {
        UserInfoDto userInfo = userService.infoUser();

        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), null));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), userInfo));
    }


    //프로필 및 계정 수정
    @PatchMapping()
    @Operation(summary = "프로필 및 계정 수정")
    public ResponseEntity<?> patchUserInfo(@RequestPart(required = false) MultipartFile profilePic, @RequestPart @Valid UserUpdateReq p) {
        log.info("updateUserInfo > UserUpdateReq > p: {}", p);
        UserUpdateRes userUpdateRes = userService.patchUser(profilePic, p);
        if (userUpdateRes == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return  ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), userUpdateRes));
    }

    //비밀번호 변경
    @PatchMapping("/password")
    @Operation(summary = "비밀번호 변경")
    public ResponseEntity<?> changeUserPassword(@RequestBody ChangePasswordReq req) {
        userService.changePassword(req);
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), "비밀번호가 변경되었습니다."));
    }

    // 임시 비밀번호 발급
    @PostMapping("password")
    @Operation(summary = "임시 비밀번호 전송")
    public ResponseEntity<?> resetPassword(@RequestBody TemporaryPwDto temporaryPwDto) {
        int result = userService.temporaryPw(temporaryPwDto);
        if (result < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(ResponseCode.BAD_GATEWAY.getCode(), 0));
        }
        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }

    //임시 비밀번호 발급 여부
    @GetMapping("check-temp-password")
    @Operation(summary = "임시 비밀번호 발급 여부", description = "임시 비밀번호 발급 받았으면 true, 임시 비밀번호에서 다시 수정했으면 false")
    public ResponseEntity<Map<String, Object>> checkTempPassword(@RequestParam String email) {
        boolean isSame = userService.checkTempPassword(email);

        Map<String, Object> response = new HashMap<>();
        response.put("isSame", isSame);

        if (isSame) {
            response.put("message", "임시 비밀번호를 변경해주세요.");
        }

        return ResponseEntity.ok(response);
    }
}