package com.green.project_quadruaple.entity.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.time.LocalDate;

@Getter
@Entity
@Immutable
@Subselect("SELECT * FROM depay")
@EqualsAndHashCode
@IdClass(DepayIds.class)
public class Depay {

    @Id
    @Column(name = "de_id")
    private Long deId;

    @Column(name="expense_for")
    private String expenseFor;

    @Column(nullable = false)
    private int price;

    @Id
    @Column(name="trip_id", nullable=false)
    private Long tripId;

    @Column(nullable = false)
    private String title;

    @Column(name = "start_at", nullable = false)
    private LocalDate startAt;

    @Column(name = "end_at", nullable=false)
    private LocalDate endAt;

    @Id
    @Column(name = "user_id", nullable=false)
    private Long userId;

    @Column(name= "profile_pic")
    private String profilePic;

    @Column(nullable = false)
    private String name;

}
