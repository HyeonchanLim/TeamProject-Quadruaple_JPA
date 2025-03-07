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

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "strf_id", nullable = false)
    private StayTourRestaurFest stayTourRestaurFest;

    @Column(nullable = false,length = 50)
    private String title;

    @Column(nullable = false)
    private int price;

    @Column(name = "menu_pic", length = 200)
    private String menuPic;
}
