package com.green.project_quadruaple.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LocationDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_detail_id")
    private Long locationDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "detail_title", length = 50)
    private String detailTitle;
}
