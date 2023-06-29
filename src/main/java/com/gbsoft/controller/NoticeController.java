package com.gbsoft.controller;

import com.gbsoft.dto.LoginForm;
import com.gbsoft.service.LoginService;
import com.gbsoft.util.AesEncDec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    private final LoginService loginService;

    @RequestMapping("/userHome")
    public String userHome(String writerId) {
        if(loginService.validateAndIssueRefreshToken(AesEncDec.encrypt(writerId))){
            return "userHome";
        } else {
            return "error";
        }
    }
    @GetMapping("/notice")
    public String noticeForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }
}
