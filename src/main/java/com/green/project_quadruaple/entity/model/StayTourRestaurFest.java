package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.CreatedAt;
import com.green.project_quadruaple.trip.model.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stay_tour_restaur_fest")
public class StayTourRestaurFest extends CreatedAt {

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_detail_id", nullable = false)
    private LocationDetail locationDetail;

    @Column(length = 50)
    private String post;

    @Column(length = 50)
    private String tell;

    @Column(name = "start_at")
    private LocalDate startAt;

    @Column(name = "end_at")
    private LocalDate endAt;

    @Column(name = "open_check_in")
    private LocalTime openCheckIn;

    @Column(name = "close_check_out")
    private LocalTime closeCheckOut;

    @Column(columnDefinition = "TEXT")
    private String detail;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "busi_num" ,nullable = false)
    private BusinessNum busiNum;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private Integer state;
}
