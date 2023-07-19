package com.gbsoft.dto;


import com.gbsoft.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import java.util.List;

@Getter
public class UserFindDto {
    @ApiModelProperty
    List<User> users;

    public UserFindDto(List<User> users) {
        this.users = users;
    }
}



