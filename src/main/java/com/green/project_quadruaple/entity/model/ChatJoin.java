package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.common.config.jwt.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "chat_join",
        uniqueConstraints = {
                @UniqueConstraint(name = "chat_join_unique", columnNames = { "user_id", "role" })
        }
)
public class ChatJoin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cj_id")
    private Long cjId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false),
            @JoinColumn(name = "role", referencedColumnName = "role", insertable = false, updatable = false)
    })
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @CreationTimestamp
    @Column(name = "joinedAt", nullable = false)
    private LocalDateTime joinedAt;

}