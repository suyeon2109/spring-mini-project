package com.gbsoft.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class UserLoginResponse {
    @ApiModelProperty(example = "200")
    private Integer code;
    @ApiModelProperty(example = "로그인 되었습니다")
    private String message;
    @ApiModelProperty(example = "{\n" +"        \"writerId\": \"test\"\n" +"}")
    private Map<String,Object> payload;
}



