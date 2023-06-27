package com.gbsoft.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue
    private Long id;
    @NotNull
    private String writerId;
    @NotNull
    private String writerPwd;
    @NotNull
    private String writerName;
    @NotNull
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;



}
