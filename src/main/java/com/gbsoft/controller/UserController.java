package com.gbsoft.controller;

import com.gbsoft.domain.User;
import com.gbsoft.dto.UserForm;
import com.gbsoft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/join")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/createUserForm";
    }

    @PostMapping("/users/join")
    @ResponseBody
    public String create(UserForm form) {
        try{
            userService.join(form);
            return "가입을 축하합니다";
        } catch (IllegalStateException e){
            return e.getMessage();
        }
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "users/userList";
    }
}
