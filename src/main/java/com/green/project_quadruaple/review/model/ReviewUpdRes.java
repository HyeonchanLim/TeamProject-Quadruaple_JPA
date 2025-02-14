package com.green.project_quadruaple.review.model;

import com.green.project_quadruaple.common.config.enumdata.ResponseCode;
import com.green.project_quadruaple.common.model.ResultResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewUpdRes extends ResultResponse {
    public ReviewUpdRes() {
        super(ResponseCode.OK.getCode());
    }
    private long reviewId;
}
