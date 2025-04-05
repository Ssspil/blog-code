package com.ssspil.websocket.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ChatController {

    // 채팅 페이지로 이동
    @GetMapping("/chat")
    public String chatPage() {
        return "chat";
    }
}
