package com.gbsoft.dto;

import com.gbsoft.domain.Notice;
import com.gbsoft.dto.NoticeFindForm;
import com.gbsoft.util.Pagination;
import lombok.Getter;

import java.util.List;

@Getter
public class NoticeFindDto {
    private NoticeFindForm form;
    private List<Notice> noticeList;
    private Pagination pagination;

    public NoticeFindDto(NoticeFindForm form, List<Notice> noticeList, Pagination pagination) {
        this.form = form;
        this.noticeList = noticeList;
        this.pagination = pagination;
    }
}
