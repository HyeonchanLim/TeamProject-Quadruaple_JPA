package com.green.project_quadruaple.entity.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class ChatReceiveId implements Serializable {

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "listener_id")
    private Long listenerId;
}
