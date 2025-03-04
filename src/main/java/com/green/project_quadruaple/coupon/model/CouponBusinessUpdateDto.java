package com.green.project_quadruaple.coupon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CouponBusinessUpdateDto {
    @Schema(title="쿠폰 PK")
    private long couponId;

    @Schema(title="쿠폰 이름", example = "조기 예약 쿠폰")
    private String title;

    @Schema(example = "2025-12-31T00:00:00", type = "string")
    private LocalDateTime expiredAt;

    @Schema(title="할인율", example = "10")
    private int discountPer;

    @Schema(example = "2025-01-01T00:00:00", type = "string")
    private LocalDateTime distributeAt;
}
