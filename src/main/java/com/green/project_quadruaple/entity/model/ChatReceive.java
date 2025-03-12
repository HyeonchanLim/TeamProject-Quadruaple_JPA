package com.green.project_quadruaple.entity.model;

import com.green.project_quadruaple.entity.ids.ChatReceiveId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ChatReceive {

    @EmbeddedId
    private ChatReceiveId id = new ChatReceiveId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("chatId")
    @JoinColumn(name = "chat_id", nullable = false, insertable = false, updatable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("listenerId")
    @JoinColumn(name = "listener_id", nullable = false, insertable = false, updatable = false)
    private ChatJoin listenerId;

}

