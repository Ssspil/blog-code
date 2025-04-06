package com.ssspil.websocket.model.entity;


import com.ssspil.websocket.model.common.RoomType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "CTAT_ROOM")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @Column(name = "ROOM_ID")
    private String roomId;              // 채팅방ID

    @Column(name = "ROOM_TYPE")
    @Enumerated(EnumType.STRING)
    private RoomType roomType;          // 채팅방 타입

    @Column(name = "CREATE_AT")
    private LocalDateTime createAt;     // 방 생성 시각

}
