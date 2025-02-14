package com.green.project_quadruaple.expense.model.req;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@ToString
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DutchReq {
    @Schema(title = "지불금액",description = "총지불금액", example = "32180")
    private int totalPrice;
    @Schema(title = "어느 여행",description = "어느여행에 쓴돈", example = "1")
    private long tripId;
    @Schema(title = "제외인원",description = "지불인원에서 x로 빠진 user목록", example = "[104, 108]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<Long> exceptUsers;
}
