package com.argument.resolver.web;

import com.argument.resolver.domain.Client.ClientRequest;
import com.argument.resolver.domain.resolver.CliectInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by rius0918@gmail.com on 2024. 3. 27.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */

@Slf4j
@Controller
@RequestMapping("/spring-mvc")
public class Contoller {

    // 페이지 이동
    @GetMapping("/{name}")
    public String detail(@CliectInfo ClientRequest clientRequest, @PathVariable String name, Model model){

        // clientRequest 객체 가지고 로직 수행

        model.addAttribute("name", name);
        return "detail";
    }

    // Api 일 경우
    @GetMapping("/api/{name}")
    @ResponseBody
    public String apiResponse(@CliectInfo ClientRequest clientRequest, @PathVariable String name){

        // clientRequest 객체 가지고 로직 수행

        return name;
    }
}
