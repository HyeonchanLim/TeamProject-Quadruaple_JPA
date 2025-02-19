package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Location {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(unique = true, nullable = false, length = 50)
    private String title;

    @Column(name = "location_pic", length = 200)
    private String locationPic;

    public Location(String title, String locationPic) {
        this.title = title;
        this.locationPic = locationPic;
    }
}
