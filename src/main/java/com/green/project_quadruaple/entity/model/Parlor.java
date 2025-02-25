package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parlor {
    @Id
    private Long menuId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false)
    private int maxCapacity;

    @Column(nullable = false)
    private int recomCapacity;

    private int surcharge;
}

