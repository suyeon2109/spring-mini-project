package com.gbsoft.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoticeFindForm {
    private String searchType;
    private String keyword;
    private String sortBy;
    private String descAsc;

}
