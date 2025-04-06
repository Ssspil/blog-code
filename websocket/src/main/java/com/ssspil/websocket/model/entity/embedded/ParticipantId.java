package com.ssspil.websocket.model.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ParticipantId {

    @Column(name = "USER_NO")
    private Integer userNo;

    @Column(name ="ROOM_ID")
    private String roomId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantId that = (ParticipantId) o;
        return Objects.equals(userNo, that.userNo) && Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNo, roomId);
    }
}
