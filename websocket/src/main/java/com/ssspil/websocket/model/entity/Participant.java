package com.ssspil.websocket.model.entity;

import com.ssspil.websocket.model.common.EnterStatus;
import com.ssspil.websocket.model.entity.embedded.ParticipantId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "PARTICIPANT")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participant {

    @EmbeddedId
    private ParticipantId participantId;        // 복합키

    @MapsId("userNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private UserInfo userInfo;                  // 유저정보

    @MapsId("roomId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    private ChatRoom chatRoom;              // 채팅방 정보

    @Column(name = "ROOM_AUTHORITY")
    private String roomAuthority;           // 방 권한

    @Column(name = "CHAT_BLOCK")
    private String chatBlock;               // 채팅 차단 여부

    @Column(name = "ENTER_STATUS")
    @Enumerated(EnumType.STRING)
    private EnterStatus enterStatus;        // 방 진입 상태

    @Column(name = "ENTER_AT")
    private LocalDateTime enterAt;          // 채팅방 진입 시각
}
