package com.gbsoft.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoticeForm {
    private String title;
    private String content;
    private String note;
    private String loginUserId;
}
