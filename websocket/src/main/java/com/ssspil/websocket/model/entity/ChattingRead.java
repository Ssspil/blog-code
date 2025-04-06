package com.ssspil.websocket.model.entity;

import com.ssspil.websocket.model.entity.embedded.ChattingReadId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "CHATTING_RAED")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChattingRead {

    @EmbeddedId
    private ChattingReadId chattingReadId;      // 복합키

    @MapsId("chatId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ID")
    private Chatting chat;              // 채팅 정보

    @Column(name = "READ_AT")
    private LocalDateTime readAt;       // 읽은 시각
}
