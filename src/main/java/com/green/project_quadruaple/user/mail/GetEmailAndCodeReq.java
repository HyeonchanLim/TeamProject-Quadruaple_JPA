package com.green.project_quadruaple.user.mail;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "인증코드 확인")
public class GetEmailAndCodeReq {

    @NotNull
    @Schema(title = "수신 이메일", type= "string", example = "test@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotNull
    @Schema(title = "인증 코드", type= "string", example = "1234", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;
}
