package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PointCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_card_id")
    private Long pointCardId;

    @Column(nullable = false)
    private int available;

    @Column(nullable = false)
    private int discountPer;

    @Column(nullable = false)
    private int finalPayment;

    public PointCard(int available, int discountPer, int finalPayment) {
        this.available = available;
        this.discountPer = discountPer;
        this.finalPayment = finalPayment;
    }
}
