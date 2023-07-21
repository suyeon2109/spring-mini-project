package com.gbsoft.controller;

import com.gbsoft.domain.User;
import com.gbsoft.dto.UserFindForm;
import com.gbsoft.dto.UserForm;
import com.gbsoft.dto.CommonResponse;
import com.gbsoft.dto.ErrorResponse;
import com.gbsoft.dto.UserFindDto;
import com.gbsoft.service.UserService;
import com.gbsoft.util.AesEncDec;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@ApiOperation(value = "")
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    @ApiOperation(value = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description ="회원가입 성공"),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description ="중복된 회원 존재", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<String> create(@Valid @RequestBody UserForm form, BindingResult e) {
        if(e.hasErrors()) {
            throw new IllegalArgumentException(e.getFieldErrors().get(0).getDefaultMessage());
        } else {
            String writerId = userService.join(form);
            CommonResponse<String> response = CommonResponse.<String>builder()
                    .code(HttpStatus.CREATED.value())
                    .message("가입되었습니다.")
                    .payload(AesEncDec.decrypt(writerId))
                    .build();
            return response;
        }
    }

//    @GetMapping("/users/all")
//    @ApiOperation(value = "전체회원 조회")
//    @ResponseStatus(HttpStatus.OK)
//    public CommonResponse<UserFindDto> list() {
//        List<User> users = userService.findAllUsers();
//        UserFindDto dto = new UserFindDto(users);
//
//        CommonResponse<UserFindDto> response = CommonResponse.<UserFindDto>builder()
//                .code(HttpStatus.OK.value())
//                .message("전체회원 조회")
//                .payload(dto)
//                .build();
//
//        return response;
//    }

    @GetMapping("/users")
    @ApiOperation(value = "회원 조회, 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="회원 검색"),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CommonResponse<UserFindDto> search(@Valid @ModelAttribute UserFindForm form, BindingResult e) {
        if(e.hasErrors()) {
            throw new IllegalArgumentException(e.getFieldErrors().get(0).getDefaultMessage());
        } else {
            List<User> users = userService.findUsers(form);
            UserFindDto dto = new UserFindDto(users);

            CommonResponse<UserFindDto> response = CommonResponse.<UserFindDto>builder()
                    .code(HttpStatus.OK.value())
                    .message("회원 조회, 검색")
                    .payload(dto)
                    .build();
            return response;
        }
    }
}
