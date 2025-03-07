package com.green.project_quadruaple.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public class UpdatedAt extends CreatedAt {

    @Column(name = "updated_at")
    @UpdateTimestamp
    @Setter
    private LocalDateTime updatedAt;
}
