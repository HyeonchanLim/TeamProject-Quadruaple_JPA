package com.green.project_quadruaple.memo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemoDto {
    @JsonIgnore
    private Long memoId;
    private Long tripId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String content;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}


