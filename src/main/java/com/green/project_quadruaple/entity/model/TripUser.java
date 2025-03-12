package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.CreatedAt;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TripUser extends CreatedAt {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_user_id")
    private Long tripUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private int disable;
}
