package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.ids.ChatReceiveId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatReceive {

    @EmbeddedId
    private ChatReceiveId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false, insertable = false, updatable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listener_id", nullable = false, insertable = false, updatable = false)
    private ChatJoin listenerId;

}

