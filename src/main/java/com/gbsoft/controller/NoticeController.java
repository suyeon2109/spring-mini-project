package com.gbsoft.controller;

import com.gbsoft.domain.LoginUser;
import com.gbsoft.domain.Notice;
import com.gbsoft.dto.NoticeFindForm;
import com.gbsoft.dto.NoticeForm;
import com.gbsoft.service.NoticeService;
import com.gbsoft.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @RequestMapping("/userHome")
    public String userHome() {
        return "userHome";
    }
    @GetMapping("/notice/regist")
    public String noticeForm(Model model) {
        model.addAttribute("noticeForm", new NoticeForm());
        return "notice/createNoticeForm";
    }

    @PostMapping("/notice/regist")
    @ResponseBody
    public Map<String,String> create(@Valid NoticeForm form, @LoginUser String writerId, BindingResult e) {
        Map<String, String> map = new HashMap<>();
        noticeService.create(form, writerId);

        if(e.hasErrors()) {
            map.put("message", e.getFieldErrors().get(0).getDefaultMessage());
            map.put("redirectUrl", "");
        } else {
            map.put("message", "공지사항이 등록되었습니다.");
            map.put("redirectUrl", "/userHome");
        }
        return map;
    }

    @GetMapping("/notice/list")
    public String search(NoticeFindForm form, Model model, @RequestParam(defaultValue = "1") int page) {
        int totalListCount = noticeService.findNoticeCount(form);
        Pagination pagination = new Pagination(totalListCount, page);

        int startIndex = pagination.getStartIndex();
        int pageSize = pagination.getPageSize(); // 페이지 당 보여지는 게시글의 최대 개수

        List<Notice> noticeList = noticeService.searchNotice(form, startIndex, pageSize);
        model.addAttribute("noticeFindForm", form);
        model.addAttribute("noticeList", noticeList);
//        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("pagination", pagination);
        model.addAttribute("sortBy", form.getSortBy());
        model.addAttribute("descAsc", form.getDescAsc());

        return "notice/noticeList";
    }

    @PostMapping("/notice/{ids}/delete/{page}")
    public String delete(@PathVariable String ids, @PathVariable int page, NoticeFindForm form) {
        noticeService.delete(ids);

        String redirectUri = "/notice/list?&page="+page+"&searchType="+form.getSearchType()+"&keyword="+form.getKeyword()+"&sortBy="+form.getSortBy()+"&descAsc="+form.getDescAsc();
        return "redirect:"+redirectUri;
    }

    @GetMapping("/notice/{id}/edit")
    public String noticeEditForm(Model model, @PathVariable Long id) {
        Notice notice = noticeService.findNoticeById(id);

        model.addAttribute("noticeForm", notice);
        return "notice/updateNoticeForm";
    }

    @PostMapping("/notice/{id}/edit")
    @ResponseBody
    public Map<String,String> update(@Valid NoticeForm form, @PathVariable Long id, @LoginUser String writerId, BindingResult e) {
        Map<String, String> map = new HashMap<>();
        noticeService.update(form, id, writerId);

        if(e.hasErrors()) {
            map.put("message", e.getFieldErrors().get(0).getDefaultMessage());
            map.put("redirectUrl", "");
        } else {
            map.put("message", "공지사항이 수정되었습니다.");
            map.put("redirectUrl", "/notice/list?searchType=title&keyword=&sortBy=createdAt&descAsc=desc");
        }
        return map;
    }

}
