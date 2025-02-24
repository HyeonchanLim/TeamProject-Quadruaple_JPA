package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StrfPic {

    @Id
    @Column(name = "pic_name", length = 100)
    private String picName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strf_id", nullable = false)
    private StayTourRestaurFest strfId;

    public StrfPic(String picName, StayTourRestaurFest strfId) {
        this.picName = picName;
        this.strfId = strfId;
    }
}
