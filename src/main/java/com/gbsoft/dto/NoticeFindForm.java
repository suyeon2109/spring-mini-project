package com.gbsoft.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter @Setter
public class NoticeFindForm {
    @ApiModelProperty(example = "title", required = true)
    private String searchType;
    @ApiModelProperty(example = "keyword", allowEmptyValue = true)
    private String keyword;
    @ApiModelProperty(example = "title/createdAt", required = true)
    private String sortBy;
    @ApiModelProperty(example = "desc/asc", required = true)
    private String descAsc;

}
