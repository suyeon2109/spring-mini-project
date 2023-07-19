package com.gbsoft.controller;

import com.gbsoft.domain.User;
import com.gbsoft.domain.UserToken;
import com.gbsoft.dto.LoginForm;
import com.gbsoft.dto.CommonResponse;
import com.gbsoft.dto.ErrorResponse;
import com.gbsoft.service.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@ApiOperation(value = "")
public class LoginController {
    private final LoginService loginService;
    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="로그인 성공"),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description ="가입되지 않은 회원", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CommonResponse<String> login(@Valid @RequestBody LoginForm form, BindingResult e, HttpServletResponse response) {
        if(e.hasErrors()) {
            throw new IllegalArgumentException(e.getFieldErrors().get(0).getDefaultMessage());
        } else {
            User user = loginService.login(form);
            loginService.deleteTokenList(user);
            UserToken token = loginService.saveToken(user);
            loginService.saveCookie(token.getAccessToken(), response);

            CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                    .code(HttpStatus.OK.value())
                    .message("로그인 되었습니다.")
                    .payload(form.getWriterId())
                    .build();
            return commonResponse;
        }
    }
}
