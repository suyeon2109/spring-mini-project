package com.gbsoft.controller;

import com.gbsoft.domain.User;
import com.gbsoft.dto.UserFindForm;
import com.gbsoft.dto.UserForm;
import com.gbsoft.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@ApiOperation(value = "UserController")
public class UserController {
    private final UserService userService;

    @GetMapping("/users/join")
    @ApiIgnore
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/createUserForm";
    }

    @PostMapping("/users/join")
    @ResponseBody
    @ApiOperation(value = "회원가입")
    public Map<String,String> create(@Valid UserForm form, BindingResult e) {
        Map<String, String> map = new HashMap<>();
        try{
            if(e.hasErrors()) {
                map.put("message", e.getFieldErrors().get(0).getDefaultMessage());
                map.put("redirectUrl", "");
            } else {
                userService.join(form);
                map.put("message", "가입을 축하합니다");
                map.put("redirectUrl", "/");
            }
            return map;
        } catch (IllegalStateException ex){
            map.put("message", ex.getMessage());
            map.put("redirectUrl", "");
            return map;
        }
    }

    @GetMapping("/users")
    public String list(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("userFindForm", new UserFindForm());
        model.addAttribute("users", users);
        return "users/userList";
    }

    @GetMapping("/users/find")
    public String search(UserFindForm form, Model model) {
        List<User> users = userService.searchUsers(form);
        model.addAttribute("users", users);
        return "users/userList";
    }
}
