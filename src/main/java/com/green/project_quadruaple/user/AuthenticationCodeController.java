package com.green.project_quadruaple.user;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResponseWrapper;
import com.green.project_quadruaple.entity.model.AuthenticationCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("authentication")
@Tag(name = "인증 코드")
public class AuthenticationCodeController {
    private final AuthenticationCodeService authenticationCodeService;

//    @GetMapping("getAuthentication")
//    @Operation(summary = "인증 코드 조회")
//    public ResponseEntity<?> getAuthenticationCode(@RequestParam("email") String email) {
//        Optional<AuthenticationCode> authCodeOptional = authenticationCodeService.getAuthenticationCodeByEmail(email);
//
//        if (authCodeOptional.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), "이메일 인증 코드가 존재하지 않습니다."));
//        }
//
//        AuthenticationCode authCode = authCodeOptional.get();
//        Map<String, Object> response = new HashMap<>();
//        response.put("code_num", authCode.getCodeNum()); // 인증 코드
//        response.put("email", authCode.getEmail());
//
//        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), response));
//    }

    @GetMapping("countAuthentication")
    @Operation(summary = "인증 코드 발급 횟수")
    public ResponseEntity<?> countAuthenticationCode(@RequestParam("email") String email) {
        int result = authenticationCodeService.countAuthenticationCode(email);

        if (result < 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseWrapper<>(ResponseCode.NOT_FOUND.getCode(), "이메일 인증 코드가 존재하지 않습니다."));
        }

        return ResponseEntity.ok(new ResponseWrapper<>(ResponseCode.OK.getCode(), result));
    }
}
