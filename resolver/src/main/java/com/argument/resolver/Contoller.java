package com.argument.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/spring-mvc")
public class Contoller {

    @GetMapping("{name}")
    public String detail(HttpServletRequest request, @PathVariable String name, Model model){
        model.addAttribute("name", name);
        return "detail";
    }
}
