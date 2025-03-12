package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class RestDate {
    @EmbeddedId
    private RestDateId id;

//    @Column(nullable = false, columnDefinition = "TINYINT(4) default 0", insertable = false, updatable = false)  // 중복 매핑을 방지
//    private Integer dayWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("strfId")
    @JoinColumn(name = "strf_id", referencedColumnName = "strf_id", nullable = false)  // 외래 키의 컬럼 이름을 정확히 명시
    private StayTourRestaurFest strfId;
}



