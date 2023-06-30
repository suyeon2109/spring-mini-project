package com.gbsoft.controller;

import com.gbsoft.domain.Notice;
import com.gbsoft.domain.User;
import com.gbsoft.dto.*;
import com.gbsoft.service.LoginService;
import com.gbsoft.service.NoticeService;
import com.gbsoft.util.AesEncDec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    private final LoginService loginService;
    private final NoticeService noticeService;

    @RequestMapping("/userHome")
    public String userHome(Model model, String writerId) {
        if(loginService.validateAndIssueRefreshToken(AesEncDec.encrypt(writerId))){
            model.addAttribute("loginUserId", writerId);
            return "userHome";
        } else {
            return "error";
        }
    }
    @GetMapping("/notice/regist")
    public String noticeForm(Model model, String loginUserId) {
        model.addAttribute("noticeForm", new NoticeForm());
        model.addAttribute("loginUserId", loginUserId);
        return "notice/createNoticeForm";
    }

    @PostMapping("/notice/regist")
    @ResponseBody
    public Map<String,String> create(NoticeForm form) {
        Map<String, String> map = new HashMap<>();

        noticeService.create(form);
        map.put("message", "공지사항이 등록되었습니다.");
        map.put("redirectUrl", "/userHome?writerId="+form.getLoginUserId());
        return map;
    }

    @GetMapping("/notice/list")
    public String list(Model model, String loginUserId, int offset) {
        List<Notice> noticeList = noticeService.findAllNotice(offset);
        model.addAttribute("noticeFindForm", new NoticeFindForm());
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("loginUserId", loginUserId);
        return "notice/noticeList";
    }

    @GetMapping("/notice/find")
    public String search(NoticeFindForm form, Model model, String loginUserId, int offset) {
        List<Notice> noticeList = noticeService.searchNotice(form, offset);
        model.addAttribute("noticeFindForm", new NoticeFindForm());
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("loginUserId", loginUserId);
        model.addAttribute("offset", offset);
        return "notice/noticeList";
    }

    @PostMapping("/notice/{ids}/delete/{offset}")
    public String delete(@PathVariable String ids, String loginUserId, @PathVariable int offset) {
        noticeService.delete(ids);


        String redirectUri = "/notice/list?loginUserId="+loginUserId+"&offset="+offset;
        return "redirect:"+redirectUri;
    }
}
