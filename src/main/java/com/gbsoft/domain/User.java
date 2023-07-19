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
    @ApiModelProperty
    private Long id;
    @NotNull
    @ApiModelProperty
    private String writerId;
    @NotNull
    @ApiModelProperty
    private String writerPwd;
    @NotNull
    @ApiModelProperty
    private String writerName;
    @NotNull
    @ApiModelProperty
    private LocalDateTime createdAt;
    @ApiModelProperty
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
