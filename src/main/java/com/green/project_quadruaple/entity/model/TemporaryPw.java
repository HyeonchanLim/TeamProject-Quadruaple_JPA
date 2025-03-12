package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.base.CreatedAt;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TemporaryPw extends CreatedAt {

    @Id
    private Long userId; // PK이면서 FK

    @OneToOne
    @MapsId // userId를 User의 PK로 매핑
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "tp_pw", nullable = false, length = 300)
    private String temporaryPassword;

}