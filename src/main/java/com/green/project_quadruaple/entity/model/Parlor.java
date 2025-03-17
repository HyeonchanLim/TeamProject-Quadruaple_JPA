package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Parlor {
    @Id
    private Long menuId;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false, length = 11)
    private int maxCapacity;

    @Column(nullable = false, length = 11)
    private int recomCapacity;

    @Column(nullable = false, length = 11)
    private int surcharge;
}

