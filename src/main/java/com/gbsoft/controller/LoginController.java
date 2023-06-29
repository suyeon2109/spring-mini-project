package com.gbsoft.controller;

import com.gbsoft.domain.UserToken;
import com.gbsoft.dto.LoginForm;
import com.gbsoft.dto.UserForm;
import com.gbsoft.service.LoginService;
import com.gbsoft.util.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, String> login(LoginForm form) {
        Map<String, String> map = new HashMap<>();
        try{
            loginService.login(form);
            loginService.saveToken(form);
            map.put("message", "로그인 되었습니다.");
            map.put("redirectUrl", "/userHome");
            return map;
        } catch (IllegalStateException e){
            map.put("message", e.getMessage());
            map.put("redirectUrl", "");
            return map;
        }
    }

}
