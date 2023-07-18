package com.gbsoft.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class LoginForm {
    @NotBlank(message = "아이디는 필수 값 입니다.")
    @Schema(example = "test", required = true, description = "아이디")
    private String writerId;
    @NotBlank(message = "비밀번호는 필수 값 입니다.")
    @Schema(example = "1111", required = true, description = "비밀번호")
    private String writerPwd;
}
