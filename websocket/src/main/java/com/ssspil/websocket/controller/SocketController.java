package com.ssspil.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class SocketController {

    /****************************
     * MessageMapping : 클라이언트가 보낸 주소 (config에 설정한 값)/(매핑 경로) (message/room/{roomId})
     * SendTo : 서버가 클라이언트에게 보내는 주소
     ***************************/

    @MessageMapping("/room/{roomId}")
    @SendTo("/chat/room/{roomId}")
    public String sendMessage(@DestinationVariable String roomId, @Payload String chatMessage) {
        log.info("Received message: {}", chatMessage);
        log.info("Received ROOM: {}", roomId);
        return chatMessage;
    }


}