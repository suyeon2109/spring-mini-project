package com.gbsoft.controller;

import com.gbsoft.domain.LoginUser;
import com.gbsoft.domain.Notice;
import com.gbsoft.dto.*;
import com.gbsoft.service.NoticeService;
import com.gbsoft.util.AesEncDec;
import com.gbsoft.util.Pagination;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@ApiOperation(value = "")
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping("/notice/regist")
    @ApiOperation(value = "공지사항 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description ="공지사항 등록 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<?> create(@Valid @RequestBody NoticeForm form, @LoginUser @ApiIgnore String writerId, BindingResult e) {
        if(e.hasErrors()) {
            throw new IllegalArgumentException(e.getFieldErrors().get(0).getDefaultMessage());
        } else {
            String createdWriterId =  noticeService.create(form, writerId);
            CommonResponse<String> response = CommonResponse.<String>builder()
                    .code(HttpStatus.CREATED.value())
                    .message("등록되었습니다.")
                    .payload(AesEncDec.decrypt(createdWriterId))
                    .build();
            return response;
        }
    }

    @GetMapping("/notice/list")
    @ApiOperation(value = "공지사항 조회, 검색, 정렬")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="공지사항 조회 성공"),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CommonResponse<NoticeFindDto> search(@ModelAttribute NoticeFindForm form, @RequestParam(defaultValue = "1") int page) {
        int totalListCount = noticeService.findNoticeCount(form);
        Pagination pagination = new Pagination(totalListCount, page);

        int startIndex = pagination.getStartIndex();
        int pageSize = pagination.getPageSize(); // 페이지 당 보여지는 게시글의 최대 개수

        List<Notice> noticeList = noticeService.searchNotice(form, startIndex, pageSize);
        NoticeFindDto dto = new NoticeFindDto(form, noticeList, pagination);

        CommonResponse<NoticeFindDto> response = CommonResponse.<NoticeFindDto>builder()
                                                            .code(HttpStatus.OK.value())
                                                            .message("공지사항 조회 성공")
                                                            .payload(dto)
                                                            .build();
        return response;
    }

    @DeleteMapping("/notice/{ids}/delete/{page}")
    @ApiOperation(value = "공지사항 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="공지사항 삭제 성공"),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CommonResponse<NoticeDeleteDto> delete(@PathVariable String ids, @PathVariable @ApiParam(example = "1,2,3") int page, @ModelAttribute NoticeFindForm form) {
        NoticeDeleteDto dto = new NoticeDeleteDto(form, page);

        noticeService.delete(ids);
        CommonResponse<NoticeDeleteDto> response = CommonResponse.<NoticeDeleteDto>builder()
                .code(HttpStatus.OK.value())
                .message("삭제되었습니다.")
                .payload(dto)
                .build();

        return response;
    }

    @GetMapping("/notice/{id}/edit")
    @ApiOperation(value = "공지사항 수정을 위한 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="공지사항 조회 성공"),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CommonResponse<Notice> noticeEditForm(Model model, @PathVariable @ApiParam(example = "1") Long id) {
        Notice notice = noticeService.findNoticeById(id);

        CommonResponse<Notice> response = CommonResponse.<Notice>builder()
                .code(HttpStatus.OK.value())
                .message("공지사항 조회 성공")
                .payload(notice)
                .build();

        return response;
    }

    @PutMapping("/notice/{id}/edit")
    @ApiOperation(value = "공지사항 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="공지사항 수정 성공"),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CommonResponse<?> update(@Valid @RequestBody NoticeForm form, @PathVariable @ApiParam(example = "1") Long id, @LoginUser @ApiIgnore String writerId, BindingResult e) {
        if(e.hasErrors()) {
            throw new IllegalArgumentException(e.getFieldErrors().get(0).getDefaultMessage());
        } else {
            noticeService.update(form, id, writerId);
            CommonResponse<String> response = CommonResponse.<String>builder()
                    .code(HttpStatus.OK.value())
                    .message("수정되었습니다.")
                    .payload(writerId)
                    .build();

            return response;
        }
    }

}
