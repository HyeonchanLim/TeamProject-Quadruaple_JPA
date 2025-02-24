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
public class Chat extends CreatedAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cj_id", nullable = false)
    private ChatJoin chatJoin;

    @Column(nullable = false, length = 1000)
    private String content;

    public Chat(ChatJoin chatJoin, String content) {
        this.chatJoin = chatJoin;
        this.content = content;
    }
}
