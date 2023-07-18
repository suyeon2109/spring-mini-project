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
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<CommonResponse> create(@Valid @RequestBody NoticeForm form, @LoginUser @ApiIgnore String writerId, BindingResult e) {
        Map<String, Object> map = new HashMap<>();

        if(e.hasErrors()) {
            throw new IllegalArgumentException(e.getFieldErrors().get(0).getDefaultMessage());
        } else {
            String createdWriterId =  noticeService.create(form, writerId);
            map.put("writerId", AesEncDec.decrypt(createdWriterId));
            CommonResponse response = CommonResponse.builder()
                    .code(HttpStatus.CREATED.value())
                    .message("등록되었습니다.")
                    .payload(map)
                    .build();
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
    }

    @GetMapping("/notice/list")
    @ApiOperation(value = "공지사항 조회, 검색, 정렬")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="공지사항 조회 성공", content = @Content(schema = @Schema(implementation = NoticeListResponse.class))),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<NoticeListResponse> search(@ModelAttribute NoticeFindForm form, @RequestParam(defaultValue = "1") int page) {
        int totalListCount = noticeService.findNoticeCount(form);
        Pagination pagination = new Pagination(totalListCount, page);

        int startIndex = pagination.getStartIndex();
        int pageSize = pagination.getPageSize(); // 페이지 당 보여지는 게시글의 최대 개수

        List<Notice> noticeList = noticeService.searchNotice(form, startIndex, pageSize);

        Map<String, Object> map = new HashMap<>();
        map.put("noticeFindForm", form);
        map.put("noticeList", noticeList);
        map.put("pagination", pagination);

        NoticeListResponse response = NoticeListResponse.builder()
                .code(HttpStatus.OK.value())
                .message("공지사항 조회 성공")
                .payload(map)
                .build();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/notice/{ids}/delete/{page}")
    @ApiOperation(value = "공지사항 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="공지사항 삭제 성공", content = @Content(schema = @Schema(implementation = NoticeDeleteResponse.class))),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<NoticeDeleteResponse> delete(@PathVariable String ids, @PathVariable @ApiParam(example = "1,2,3") int page, @ModelAttribute NoticeFindForm form) {
        Map<String, Object> map = new HashMap<>();
        map.put("noticeFindForm", form);
        map.put("page", page);

        noticeService.delete(ids);
        NoticeDeleteResponse response = NoticeDeleteResponse.builder()
                .code(HttpStatus.OK.value())
                .message("삭제되었습니다.")
                .payload(map)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/notice/{id}/edit")
    @ApiOperation(value = "공지사항 수정을 위한 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="공지사항 조회 성공", content = @Content(schema = @Schema(implementation = UpdateResponse.class))),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UpdateResponse> noticeEditForm(Model model, @PathVariable @ApiParam(example = "1") Long id) {
        Notice notice = noticeService.findNoticeById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("noticeForm", notice);

        UpdateResponse response = UpdateResponse.builder()
                .code(HttpStatus.OK.value())
                .message("공지사항 조회 성공")
                .payload(map)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/notice/{id}/edit")
    @ApiOperation(value = "공지사항 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description ="공지사항 수정 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description ="잘못된 파라미터", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CommonResponse> update(@Valid @RequestBody NoticeForm form, @PathVariable @ApiParam(example = "1") Long id, @LoginUser @ApiIgnore String writerId, BindingResult e) {
        Map<String, Object> map = new HashMap<>();

        if(e.hasErrors()) {
            throw new IllegalArgumentException(e.getFieldErrors().get(0).getDefaultMessage());
        } else {
            noticeService.update(form, id, writerId);
            CommonResponse response = CommonResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("수정되었습니다.")
                    .payload(map)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

}
