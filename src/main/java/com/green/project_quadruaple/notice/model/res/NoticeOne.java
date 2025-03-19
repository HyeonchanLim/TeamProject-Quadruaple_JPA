package com.green.project_quadruaple.notice.model.res;

import com.green.project_quadruaple.entity.base.NoticeCategory;
import com.green.project_quadruaple.entity.model.Notice;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
public class NoticeOne {
    private Long noticeId;
    private String title;
    private String content;
    private NoticeCategory category;
    private String noticedAt;
    private String foreignUrl;

    public NoticeOne(Notice no, LocalDateTime createdAt) {
        this.noticeId=no.getNoticeId();
        this.title=no.getTitle();
        this.content=no.getContent();
        this.category=no.getNoticeCategory();
        this.noticedAt = createdAt.toLocalDate().isEqual(LocalDate.now())?
                createdAt.toLocalTime().toString():createdAt.toLocalDate().toString();
        this.foreignUrl= switch (this.category){
            case TRIP -> { yield "http://112.222.157.157:5231/schedule/index?tripId="+no.getForeignNum(); }
            case COUPON -> { yield "http://112.222.157.157:5231/user/usercoupon" ;}
            case CHAT -> { yield "http://112.222.157.157:5231/chatroom?roomId="+no.getForeignNum(); }
            case POINT -> { yield "http://112.222.157.157:5231/user/point";}
            case BOOKING -> { yield "http://112.222.157.157:5231/user/userbooking";}
            default -> { yield null ;}
        };
    }
}
