package com.gbsoft.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class NoticeListResponse {
    @ApiModelProperty(example = "200")
    private Integer code;
    @ApiModelProperty(example = "공지사항 조회")
    private String message;
    @ApiModelProperty(example = "{\n" + "    \"noticeFindForm\": {\n" + "      \"searchType\": \"title\",\n" + "      \"keyword\": \"title\",\n" + "      \"sortBy\": \"title\",\n" + "      \"descAsc\": \"desc\"\n" + "    },\n" + "    \"noticeList\": [\n" + "      {\n" + "        \"id\": 1,\n" + "        \"title\": \"title\",\n" + "        \"content\": \"content\",\n" + "        \"note\": \"note\",\n" + "        \"createdAt\": \"2023-07-18T14:33:25.540198\",\n" + "        \"createdWriterId\": \"test\",\n" + "        \"modifiedAt\": null,\n" + "        \"modifiedWriterId\": null\n" + "      }\n" + "    ],\n" + "    \"pagination\": {\n" + "      \"pageSize\": 10,\n" + "      \"blockSize\": 10,\n" + "      \"page\": 1,\n" + "      \"block\": 1,\n" + "      \"totalListCnt\": 2,\n" + "      \"totalPageCnt\": 1,\n" + "      \"totalBlockCnt\": 1,\n" + "      \"startPage\": 1,\n" + "      \"endPage\": 1,\n" + "      \"startIndex\": 0,\n" + "      \"prevBlock\": 1,\n" + "      \"nextBlock\": 1\n" + "    }\n" + "  }")
    private Map<String,Object> payload;
}



