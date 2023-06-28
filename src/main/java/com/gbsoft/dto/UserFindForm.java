package com.gbsoft.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserFindForm {
    private String searchType;
    private String keyword;
}
