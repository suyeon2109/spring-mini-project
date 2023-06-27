package com.gbsoft.controller;

import com.gbsoft.domain.Member;
import com.gbsoft.dto.MemberForm;
import com.gbsoft.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/join")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/join")
    @ResponseBody
    public String create(MemberForm form) {
        try{
            memberService.join(form);
            return "가입을 축하합니다";
        } catch (IllegalStateException e){
            return e.getMessage();
        }
    }
}
