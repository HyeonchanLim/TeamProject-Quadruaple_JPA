package com.green.project_quadruaple.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public class CreatedAt {

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;
}
