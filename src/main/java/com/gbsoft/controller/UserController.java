package com.gbsoft.controller;

import com.gbsoft.domain.User;
import com.gbsoft.dto.*;
import com.gbsoft.service.UserService;
import com.gbsoft.util.AesEncDec;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@ApiOperation(value = "")
public class UserController {
    private final UserService userService;

    @PostMapping("/users/join")
    @ApiOperation(value = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description ="공지사항 등록 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description ="중복된 회원 존재", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommonResponse> create(@Valid @RequestBody UserForm form, BindingResult e) {
        Map<String, Object> map = new HashMap<>();

        if(e.hasErrors()) {
            throw new IllegalArgumentException(e.getFieldErrors().get(0).getDefaultMessage());
        } else {
            String writerId = userService.join(form);
            map.put("writerId", AesEncDec.decrypt(writerId));
            CommonResponse response = CommonResponse.builder()
                    .code(HttpStatus.CREATED.value())
                    .message("가입되었습니다.")
                    .payload(map)
                    .build();
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
    }

    @GetMapping("/users")
    @ApiOperation(value = "전체회원 조회")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommonResponse> list() {
        Map<String, Object> map = new HashMap<>();
        List<User> users = userService.findAllUsers();
        map.put("users", users);
        CommonResponse response = CommonResponse.builder()
                .code(HttpStatus.OK.value())
                .message("전체회원 조회")
                .payload(map)
                .build();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/users/find")
    @ApiOperation(value = "회원 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="회원 검색", content = @Content(schema = @Schema(implementation = UserFindResponse.class))),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity search(@Valid @ModelAttribute UserFindForm form, BindingResult e) {
        Map<String, Object> map = new HashMap<>();
        if(e.hasErrors()) {
            ErrorResponse response = ErrorResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(e.getFieldErrors().get(0).getDefaultMessage())
                    .description("uri=/users/find")
                    .build();
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } else {
            List<User> users = userService.searchUsers(form);
            map.put("users", users);
            CommonResponse response = CommonResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("회원 검색")
                    .payload(map)
                    .build();
            return new ResponseEntity(response, HttpStatus.OK);
        }
    }
}
