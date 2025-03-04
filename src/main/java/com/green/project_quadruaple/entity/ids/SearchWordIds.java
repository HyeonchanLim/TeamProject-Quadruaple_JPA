package com.green.project_quadruaple.entity.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SearchWordIds implements Serializable {

    @Column(nullable = false)
    private String txt;
    @Column(nullable = false)
    private Long userId;
}
