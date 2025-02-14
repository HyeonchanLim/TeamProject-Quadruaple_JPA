package com.green.project_quadruaple.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.project_quadruaple.booking.model.dto.MenuIdAndQuantityDto;
import com.green.project_quadruaple.datamanager.model.MenuReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BookingPostReq {

    @NotNull
    @JsonProperty("strf_id")
    @Schema(title = "상품 ID", type= "long", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long strfId;

    @NotBlank
    @JsonProperty("check_in")
    @Schema(title = "체크인 시각", type= "string", example = "2025-02-11 14:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private String checkIn;

    @NotBlank
    @JsonProperty("check_out")
    @Schema(title = "체크아웃 시각", type= "string", example = "2025-02-15 22:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private String checkOut;

    @JsonProperty("coupon_id")
    @Schema(title = "쿠폰 번호", type= "long", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long couponId;

    @NotNull
    @JsonProperty("actual_paid")
    @Schema(title = "쿠폰 적용한 최종 결제 금액", type= "int", example = "225000", requiredMode = Schema.RequiredMode.REQUIRED)
    private int actualPaid;

    @NotNull
    @JsonProperty("order_list")
    private List<MenuIdAndQuantityDto> orderList;

    @JsonIgnore
    private Long userId;

    @JsonIgnore
    private String tid;

    @JsonIgnore
    private Long bookingId;

    @JsonIgnore
    private Long receiveId;
}
/*
*    "strf_id" : 1,
   "visit_at" : "2025-01-16 15:03:00",
   "coupon_id" : 1
   "actual_paid" : 252220,
   "order_list" : [{"1":1},{"2":1}, ...]
* */