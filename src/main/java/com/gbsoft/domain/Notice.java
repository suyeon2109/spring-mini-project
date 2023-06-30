package com.gbsoft.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Notice {
    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String title;
    @NotNull
    private String content;
    private String note;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private String createdWriterId;
    private LocalDateTime modifiedAt;
    private String modifiedWriterId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "writer_id")
//    private User user;
//
//    public void setUser(User user) {
//        this.user = user;
//        user.getNoticeList().add(this);
//    }
}
