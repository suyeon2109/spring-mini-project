package com.gbsoft.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@Builder
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
    private List<UserToken> userTokens = new ArrayList<>();

    public User(Long id, String writerId, String writerPwd, String writerName, LocalDateTime createdAt, LocalDateTime modifiedAt, List<UserToken> userTokens) {
        this.id = id;
        this.writerId = writerId;
        this.writerPwd = writerPwd;
        this.writerName = writerName;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userTokens = userTokens;
    }

    public User() {

    }
}
