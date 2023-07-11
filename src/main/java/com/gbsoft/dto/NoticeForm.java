package com.gbsoft.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@ToString
public class NoticeForm {
    @NotBlank(message = "제목은 필수 값 입니다.")
    private String title;
    @NotBlank(message = "내용은 필수 값 입니다.")
    private String content;
    private String note;
//    private String loginUserId;
}
