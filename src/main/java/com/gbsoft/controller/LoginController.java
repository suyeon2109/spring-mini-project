package com.gbsoft.controller;

import com.gbsoft.domain.User;
import com.gbsoft.domain.UserToken;
import com.gbsoft.dto.*;
import com.gbsoft.service.LoginService;
//import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@ApiOperation(value = "")
public class LoginController {
    private final LoginService loginService;
    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="로그인 성공", content = @Content(schema = @Schema(implementation = UserLoginResponse.class))),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description ="가입되지 않은 회원", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CommonResponse> login(@Valid @RequestBody LoginForm form, BindingResult e, HttpServletResponse response) {
        if(e.hasErrors()) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(e.getFieldErrors().get(0).getDefaultMessage())
                    .description("uri=/users/join")
                    .build();
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        } else {
            Map<String, Object> map = new HashMap<>();
            User user = loginService.login(form);
            loginService.deleteTokenList(user);
            UserToken token = loginService.saveToken(user);
            loginService.saveCookie(token.getAccessToken(), response);
            map.put("writerId", form.getWriterId());

            CommonResponse commonResponse = CommonResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("로그인 되었습니다.")
                    .payload(map)
                    .build();
            return new ResponseEntity(commonResponse, HttpStatus.OK);
        }
    }
}
