package com.green.project_quadruaple.entity.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RestDateId {
    @Column(nullable = false, columnDefinition = "TINYINT(4) default 0")
    private Integer dayWeek;

    private Long strfId;
}
