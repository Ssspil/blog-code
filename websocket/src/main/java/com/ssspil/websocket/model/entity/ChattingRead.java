package com.ssspil.websocket.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "CHATTING_RAED")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChattingRead {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ID")
    private Chatting chat;          // 채팅 정보

    @Column(name = "USER_NO")
    private Integer userNo;         // 유저번호

    @Column(name = "READ_AT")
    private LocalDateTime readAt;       // 읽은 시각

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChattingRead that = (ChattingRead) o;
        return Objects.equals(chat, that.chat) && Objects.equals(userNo, that.userNo) && Objects.equals(readAt, that.readAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chat, userNo, readAt);
    }
}
