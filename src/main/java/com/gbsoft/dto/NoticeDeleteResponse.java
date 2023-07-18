package com.gbsoft.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class NoticeDeleteResponse {
    @ApiModelProperty(example = "200")
    private Integer code;
    @ApiModelProperty(example = "공지사항 삭제 성공")
    private String message;
    @ApiModelProperty(example = "{\n" + "    \"noticeFindForm\": {\n" + "      \"searchType\": \"title\",\n" + "      \"keyword\": null,\n" + "      \"sortBy\": \"title\",\n" + "      \"descAsc\": \"desc\"\n" + "    },\n" + "    \"page\": 1\n" + "  }")
    private Map<String,Object> payload;
}



