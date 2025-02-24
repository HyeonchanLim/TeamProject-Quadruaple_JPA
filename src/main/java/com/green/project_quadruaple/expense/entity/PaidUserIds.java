package com.green.project_quadruaple.expense.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class PaidUserIds implements Serializable {
    private Long tripUserId;
    private Long deId;
}
