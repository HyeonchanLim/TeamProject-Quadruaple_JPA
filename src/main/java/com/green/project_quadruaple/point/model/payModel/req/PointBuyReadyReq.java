package com.green.project_quadruaple.point.model.payModel.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PointBuyReadyReq {
    @Schema(example = "5")
    private long pointCardId;

    @JsonIgnore
    private String tid;
}
