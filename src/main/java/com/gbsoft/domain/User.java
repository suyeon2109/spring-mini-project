package com.gbsoft.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
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

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Notice> noticeList = new ArrayList<>();
}
