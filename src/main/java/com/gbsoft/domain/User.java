package com.gbsoft.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "회원 pk")
    private Long id;
    @NotNull
    @Schema(description = "회원 아이디")
    private String writerId;
    @NotNull
    @Schema(description = "회원 비밀번호")
    private String writerPwd;
    @NotNull
    @Schema(description = "회원 이름")
    private String writerName;
    @NotNull
    @Schema(description = "생성일자")
    private LocalDateTime createdAt;
    @Schema(description = "수정일자")
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
