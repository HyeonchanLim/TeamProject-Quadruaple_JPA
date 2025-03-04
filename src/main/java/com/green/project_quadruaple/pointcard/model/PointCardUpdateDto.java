package com.green.project_quadruaple.pointcard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PointCardUpdateDto {
    @Schema(title="포인트 카드 PK")
    private String pointCardId;

    @Schema(title="사용 가능 금액", example="50000")
    private int available;

    @Schema(title="할인율", example = "10")
    private int discountPer;

    @JsonIgnore
    private int finalPayment;
}
