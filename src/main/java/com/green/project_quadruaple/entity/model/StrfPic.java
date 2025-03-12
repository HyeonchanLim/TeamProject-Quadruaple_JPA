package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class StrfPic {

    @Id
    @Column(name = "pic_name", length = 100)
    private String picName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strf_id", nullable = false)
    private StayTourRestaurFest strfId;

}
