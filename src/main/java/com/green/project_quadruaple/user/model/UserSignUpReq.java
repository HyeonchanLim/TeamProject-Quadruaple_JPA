package com.green.project_quadruaple.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.project_quadruaple.common.config.jwt.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserSignUpReq {
    @JsonIgnore
    private long userId;
    @Schema(title="아이디", example = "test@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,10}$", message = "유효하지 않은 형식의 이메일입니다.")
    private String email;
    @Schema(title="비밀번호", example = "password!", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[\\d~!@#$%^&*()_+=])[A-Za-z\\d~!@#$%^&*()_+=]{8,20}$",
            message = "비밀번호는 특수문자, 숫자, 영문자 중 두 가지 이상을 포함한 8자 이상 20자 이하여야 합니다.")
    private String pw;
    @Schema(title="이름(닉네임)", description = "중복 불가", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(title="생년월일", example = "2000-01-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate birth;
    @Schema(title="전화번호", example = "010-0000-0000")
    private String tell;
    @JsonIgnore
    private String profilePic;
    @JsonIgnore
    private int state;
    @JsonIgnore
    private int verified;
    @JsonIgnore
    private List<UserRole> role = new ArrayList<>();
}
