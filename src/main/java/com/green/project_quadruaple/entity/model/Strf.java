package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Strf {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "strf_id")
    private Long strfId;
}
