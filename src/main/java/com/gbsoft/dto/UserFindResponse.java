package com.gbsoft.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class UserFindResponse {
    @ApiModelProperty(example = "200")
    private Integer code;
    @ApiModelProperty(example = "회원조회")
    private String message;
    @ApiModelProperty(example = "{\n" + "    \"users\": [\n" + "      {\n" + "        \"id\": 1,\n" + "        \"writerId\": \"test\",\n" + "        \"writerPwd\": \"IgY2v7ifbzD8iz9HVhomIQ==\",\n" + "        \"writerName\": \"홍길동\",\n" + "        \"createdAt\": \"2023-07-18T09:16:50.526062\",\n" + "        \"modifiedAt\": null\n" + "      }\n" + "    ]\n" + "  }")
    private Map<String,Object> payload;
}



