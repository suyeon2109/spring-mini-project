package com.gbsoft.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UserToken {
    @Id @GeneratedValue
    private Long seq;

//    private String writerId;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @ColumnDefault("'Y'")
    private String useYn;

    private String userAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
        user.getUserTokens().add(this);
    }

}
