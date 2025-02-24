package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strf_id", nullable = false)
    private StayTourRestaurFest stayTourRestaurFest;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int price;

    private String menuPic;
}
