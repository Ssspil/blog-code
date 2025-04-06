package com.ssspil.websocket.model.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ChattingReadId {

    @Column(name = "CHAT_ID")
    private String chatId;

    @Column(name = "USER_NO")
    private Integer userNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChattingReadId that = (ChattingReadId) o;
        return Objects.equals(chatId, that.chatId) && Objects.equals(userNo, that.userNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, userNo);
    }
}
