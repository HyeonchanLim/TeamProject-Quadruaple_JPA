package com.green.project_quadruaple.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Refund {

    @Column(name = "refund", nullable = false, columnDefinition = "int default 0")
    private int refund;
}
