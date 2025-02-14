package com.green.project_quadruaple.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserSignInReq {
    @Schema(title="아이디", example = "ksj23444@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,10}$", message = "유효하지 않은 형식의 이메일입니다.")
    private final String email;
    @Schema(title="비밀번호", example = "password!", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[\\d~!@#$%^&*()_+=])[A-Za-z\\d~!@#$%^&*()_+=]{8,20}$",
            message = "비밀번호는 특수문자, 숫자, 영문자 중 두 가지 이상을 포함한 8자 이상 20자 이하여야 합니다.")
    private final String pw;
}
