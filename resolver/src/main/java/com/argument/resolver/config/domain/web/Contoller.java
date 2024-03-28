package com.argument.resolver.config.domain.web;

import com.argument.resolver.config.domain.Client.ClientRequest;
import com.argument.resolver.config.domain.resolver.CliectInfo;
import jakarta.servlet.http.HttpServletRequest;
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
        log.info("컨트롤러 : {}", clientRequest);
        model.addAttribute("name", name);
        return "detail";
    }

    // Api 일 경우
    @GetMapping("/api/{name}")
    @ResponseBody
    public String apiResponse(@CliectInfo ClientRequest clientRequest, @PathVariable String name){
        log.info("컨트롤러 : {} ", clientRequest);
        return name;
    }
}
