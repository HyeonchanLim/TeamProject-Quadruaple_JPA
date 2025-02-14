package com.green.project_quadruaple.expense.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DeDto {
    @JsonIgnore
    private Long deId;
    @JsonProperty("paid_for")
    @Schema(title = "사용처",description = "어디에 사용했는지", example = "컴퍼스 커피")
    private String paidFor;
}
