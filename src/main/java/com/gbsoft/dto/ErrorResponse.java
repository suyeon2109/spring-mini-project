package com.gbsoft.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    @ApiModelProperty(example = "400 / 409")
    private Integer code;
    @ApiModelProperty(example = "잘못된 값 입력 / 이미 존재하는 회원입니다. / 지원하지 않는 검색조건 입니다. / 가입되지 않은 회원입니다.")
    private String message;
    @ApiModelProperty(example = "uri=/users/join")
    private String description;
}
