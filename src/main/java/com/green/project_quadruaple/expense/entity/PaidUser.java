package com.green.project_quadruaple.expense.entity;

import com.green.project_quadruaple.entity.model.TripUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
