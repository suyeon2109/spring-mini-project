package com.gbsoft.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UserForm {
    @NotBlank(message = "아이디는 필수 값 입니다.")
    private String writerId;
    @NotBlank(message = "비밀번호는 필수 값 입니다.")
    private String writerPwd;
    @NotBlank(message = "이름은 필수 값 입니다.")
    private String writerName;
}
