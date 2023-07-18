package com.gbsoft.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class UpdateResponse {
    @ApiModelProperty(example = "200")
    private Integer code;
    @ApiModelProperty(example = "공지사항 조회 성공")
    private String message;
    @ApiModelProperty(example = "{\n" + "    \"noticeForm\": {\n" + "      \"id\": 1,\n" + "      \"title\": \"title\",\n" + "      \"content\": \"content\",\n" + "      \"note\": \"note\",\n" + "      \"createdAt\": \"2023-07-18T14:33:25.540198\",\n" + "      \"createdWriterId\": \"wDdkSWSbODuVvjP0lUydpg==\",\n" + "      \"modifiedAt\": null,\n" + "      \"modifiedWriterId\": null\n" + "    }\n" + "  }")
    private Map<String,Object> payload;
}



