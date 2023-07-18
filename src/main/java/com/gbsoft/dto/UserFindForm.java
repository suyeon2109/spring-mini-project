package com.gbsoft.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UserFindForm {
    @NotBlank(message = "검색타입은 필수 값 입니다.")
    private String searchType;
    private String keyword;
}
