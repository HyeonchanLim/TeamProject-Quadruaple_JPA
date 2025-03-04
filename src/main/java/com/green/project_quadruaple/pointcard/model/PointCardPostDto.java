package com.green.project_quadruaple.pointcard.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PointCardPostDto {
    @Schema(title="사용 가능 금액", example="50000")
    private int available;

    @Schema(title="할인율", example = "10")
    private int discountPer;
}
