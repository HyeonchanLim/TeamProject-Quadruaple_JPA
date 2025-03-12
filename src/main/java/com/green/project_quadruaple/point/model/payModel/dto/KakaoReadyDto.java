package com.green.project_quadruaple.point.model.payModel.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.project_quadruaple.booking.model.BookingPostReq;
import com.green.project_quadruaple.entity.model.Booking;
import com.green.project_quadruaple.entity.model.PointHistory;
import com.green.project_quadruaple.point.model.req.PointHistoryPostReq;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoReadyDto {

    private String tid;

    @JsonProperty("next_redirect_pc_url")
    private String nextRedirectPcUrl;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonIgnore
    private String partnerOrderId;

    @JsonIgnore
    private String partnerUserId;

    @JsonIgnore
    private PointHistory pointHistory;

    @JsonIgnore
    private Integer remainPoint;
}
