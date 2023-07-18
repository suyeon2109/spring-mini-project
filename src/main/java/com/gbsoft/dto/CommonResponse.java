package com.gbsoft.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class CommonResponse {
    @ApiModelProperty(example = "201 / 200")
    private Integer code;
    @ApiModelProperty(example = "등록되었습니다. / 수정되었습니다. / 삭제되었습니다.")
    private String message;
    @ApiModelProperty(example = "{\n" +"        \"writerId\": \"test\"\n" +"}")
    private Map<String,Object> payload;
}



