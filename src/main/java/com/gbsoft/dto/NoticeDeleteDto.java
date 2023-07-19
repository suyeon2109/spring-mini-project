package com.gbsoft.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class NoticeDeleteDto {
    @ApiModelProperty
    private NoticeFindForm form;
    @ApiModelProperty
    private int page;

    public NoticeDeleteDto(NoticeFindForm form, int page) {
        this.form = form;
        this.page = page;
    }
}
