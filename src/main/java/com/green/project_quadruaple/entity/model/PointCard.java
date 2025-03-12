package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Random;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PointCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_card_id",nullable = false)
    private Long pointCardId;

    @Column(nullable = false)
    private int available;

    @Column(nullable = false)
    private int discountPer;

    @Column(name = "final_payment", nullable = false)
    private int finalPayment;

}
