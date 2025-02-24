package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.Refund;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PurchasedPoint extends Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchased_point_id")
    private Long purchasedPointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_card_id", nullable = false)
    private PointCard pointCard;

    @Column(name = "purchase_at", nullable = false)
    private LocalDateTime purchaseAt;

    @Column(nullable = false, length = 30)
    private String tid;

    @Column(nullable = false)
    private int remain;

    public PurchasedPoint(User user, PointCard pointCard, String tid, int remain) {
        this.user = user;
        this.pointCard = pointCard;
        this.purchaseAt = LocalDateTime.now(); // 현재 시간으로 기본값 설정
        this.tid = tid;
        this.remain = remain;
    }
}
