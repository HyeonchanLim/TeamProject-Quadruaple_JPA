package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointCard {

    @Id
    @Column(name = "point_card_id",nullable = false, columnDefinition = "CHAR(10)")
    private String pointCardId = String.format("%010d", new Random().nextInt(1000000000));

    @Column(nullable = false)
    private int available;

    @Column(nullable = false)
    private int discountPer;

    @Column(name = "final_payment", nullable = false)
    private int finalPayment;

}
