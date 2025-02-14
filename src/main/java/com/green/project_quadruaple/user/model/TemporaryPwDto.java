package com.green.project_quadruaple.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TemporaryPwDto {
    @Schema(title="아이디", example = "test@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @JsonIgnore
    private String tpPw;
    @JsonIgnore
    private long userId;
}
