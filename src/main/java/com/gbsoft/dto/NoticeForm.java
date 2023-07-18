package com.gbsoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@ToString
public class NoticeForm {
    @NotBlank(message = "제목은 필수 값 입니다.")
    @Schema(example = "title", required = true, description = "제목")
    private String title;

    @NotBlank(message = "내용은 필수 값 입니다.")
    @Schema(example = "content", required = true, description = "내용")
    private String content;

    @Schema(example = "note", required = false, description = "비고")
    private String note;
//    private String loginUserId;
}
