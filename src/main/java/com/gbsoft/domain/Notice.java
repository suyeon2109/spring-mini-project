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
}
