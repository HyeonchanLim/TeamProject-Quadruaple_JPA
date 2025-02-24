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
@NoArgsConstructor
@AllArgsConstructor
public class UsedPoint extends Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "used_point_id")
    private Long usedPointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchased_point_id", nullable = false)
    private PurchasedPoint purchasedPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDateTime usedAt;

}
