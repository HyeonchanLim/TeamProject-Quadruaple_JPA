package com.green.project_quadruaple.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ResultResponseUser<T> {
    @Schema(title = "결과 메시지")
    private String resultMessage;
    @Schema(title = "결과 내용")
    private T resultData;
}
