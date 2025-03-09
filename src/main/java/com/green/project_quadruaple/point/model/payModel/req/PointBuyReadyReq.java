package com.green.project_quadruaple.point.model.payModel.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PointBuyReadyReq {
    private long pointCardId;

    private int amount;

    @JsonIgnore
    private String tid;
}
