package com.green.project_quadruaple.entity.model;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NoticeReceiveId implements java.io.Serializable {
    private Long userId;
    private Long noticeId;

}
