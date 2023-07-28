package com.gbsoft.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class NoticeDeleteDto {
    @ApiModelProperty
    private NoticeFindForm form;

    public NoticeDeleteDto(NoticeFindForm form) {
        this.form = form;
    }
}
