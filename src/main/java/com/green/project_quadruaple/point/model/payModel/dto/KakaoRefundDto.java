package com.green.project_quadruaple.point.model.payModel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.project_quadruaple.booking.model.dto.Amount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoRefundDto {

    private String aid;
    private String status;
    private Amount amount;

    @JsonProperty("approved_cancel_amount")
    private Amount approvedCancelAmount;
}
