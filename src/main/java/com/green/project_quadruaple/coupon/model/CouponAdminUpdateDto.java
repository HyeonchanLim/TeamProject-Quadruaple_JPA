package com.green.project_quadruaple.coupon.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CouponAdminUpdateDto {
    @Schema(title="쿠폰 PK")
    private long couponId;
    @Schema(title="쿠폰 이름", example = "가입 감사 쿠폰")
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredAt;
    @Schema(title="할인율", example = "10")
    private int discountPer;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime distributeAt;
}
