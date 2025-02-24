package com.green.project_quadruaple.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@MappedSuperclass
public class EndAt extends StartAt {

    @Column(name = "end_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDate endAt;
}
