package com.green.project_quadruaple.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
