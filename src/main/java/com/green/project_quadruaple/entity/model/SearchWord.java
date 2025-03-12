package com.green.project_quadruaple.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.project_quadruaple.entity.ids.SearchWordIds;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class SearchWord {

    @EmbeddedId
    private SearchWordIds searchWordIds;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @Column(name = "search_at", nullable = false)
    private LocalDateTime searchAt;
}
