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
public class TemporaryPw extends CreatedAt {

    @Id
    private Long userId; // PK이면서 FK

    @OneToOne
    @MapsId // userId를 User의 PK로 매핑
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "tp_pw", nullable = false, length = 300)
    private String temporaryPassword;

    public TemporaryPw(User user, String temporaryPassword) {
        this.user = user;
        this.userId = user.getUserId(); // User의 ID를 PK로 사용
        this.temporaryPassword = temporaryPassword;
    }
}