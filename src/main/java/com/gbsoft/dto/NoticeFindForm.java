package com.gbsoft.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class NoticeFindForm {
    @NotBlank(message = "검색타입은 필수 값 입니다.")
    @Schema(example = "title", required = true)
    private String searchType;
    @Schema(example = "keyword")
    private String keyword;
    @Schema(example = "title/createdAt", required = true)
    private String sortBy;
    @Schema(example = "desc/asc", required = true)
    private String descAsc;

}
