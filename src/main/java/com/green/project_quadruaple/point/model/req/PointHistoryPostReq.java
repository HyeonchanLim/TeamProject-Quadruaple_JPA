package com.green.project_quadruaple.point.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PointHistoryPostReq {
    private int category;
    @Schema(example = "10000")
    private int amount;
    @Schema(example = "1")
    private long relatedId;
}
