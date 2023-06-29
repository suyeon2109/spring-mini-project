package com.gbsoft.domain;

import lombok.Builder;
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

    private String writerId;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @ColumnDefault("'Y'")
    private String useYn;

    private String userAgent;

}
