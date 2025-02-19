package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.trip.model.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Strf {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "strf_id")
    private Long strfId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;

    @Column(nullable = false, length = 100)
    private String address;

    @JoinColumn(name = "location_detail_id")
    private LocationDetail
}

/*
*
location_detail_id
post
tell
start_at
end_at
open_check
close_check
rest_date
detail
created_at
busi_num
* */
