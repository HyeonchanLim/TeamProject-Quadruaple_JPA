package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.Refund;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchasedPoint extends Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchased_point_id",nullable = false)
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

}
