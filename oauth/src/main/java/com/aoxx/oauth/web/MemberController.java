package com.aoxx.oauth.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    /**
     * 회원가입 폼으로 이동
     * @return
     */
    @GetMapping("/login")
    public String loginForm(){
        return "member/loginForm";
    }
}
