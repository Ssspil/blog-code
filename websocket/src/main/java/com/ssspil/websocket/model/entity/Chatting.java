package com.ssspil.websocket.model.entity;

import com.ssspil.websocket.model.common.ChatType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "CHATTING")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chatting {

    @Id
    @Column(name = "CHAT_ID")
    private String chatId;              // 채팅ID

    @Column(name = "CHAT_TYPE")
    @Enumerated(EnumType.STRING)
    private ChatType chatType;              // 채팅 타입

    @Column(name = "MESSAGE", columnDefinition = "text")
    private String message;                 // 메시지

    @Column(name = "STATUS")
    private String status;                  // 메시지 상태

    @Column(name = "FIX")
    private String fix;                     // 고정된 메시지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "USER_NO", referencedColumnName = "USER_NO"),
            @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
    })
    private Participant participant;            // 참여자 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    private FileInfo fileinfo;          // 첨부파일

    @Column(name = "CREATE_AT")
    private LocalDateTime createAt;     // 채팅 보낸 시각

    @Column(name = "UPDATE_AT")
    private LocalDateTime updateAt;     // 채팅 수정 시각


}
