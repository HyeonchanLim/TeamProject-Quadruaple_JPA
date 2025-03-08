package com.green.project_quadruaple.point.model.req;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PointUseOrUnUseReq {
    private int category;
    private int amount;
    private long relatedId;
}
/*
            request 내용
            category(0 or 2)+참조변수+얼마
*/