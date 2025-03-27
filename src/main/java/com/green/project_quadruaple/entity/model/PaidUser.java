package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.ids.PaidUserIds;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PaidUser {
    @EmbeddedId
    private PaidUserIds paidUserIds;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @MapsId("tripUserId")
    @JoinColumn(name="trip_user_id")
    private TripUser tripUser;

    @ManyToOne
    @MapsId("deId")
    @JoinColumn(name="de_id")
    private DailyExpense dailyExpense;

}
