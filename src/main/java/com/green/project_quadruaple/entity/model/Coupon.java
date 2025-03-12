package com.green.project_quadruaple.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long couponId;

    @Column(nullable = false,length = 200)
    private String title;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Column(nullable = false)
    private int discountPer;

    @Column(name = "distribute_at", nullable = false)
    private LocalDateTime distributeAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strf_id")
    private StayTourRestaurFest strf;
}
