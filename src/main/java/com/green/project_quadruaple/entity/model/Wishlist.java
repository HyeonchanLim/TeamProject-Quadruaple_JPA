package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.CreatedAt;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Wishlist extends CreatedAt {

    @Id
    @Column(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User userId;

    @Column(name = "strf_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StayTourRestaurFest strfId;

    public Wishlist(User userId, StayTourRestaurFest strfId) {
        this.userId = userId;
        this.strfId = strfId;
    }
}
