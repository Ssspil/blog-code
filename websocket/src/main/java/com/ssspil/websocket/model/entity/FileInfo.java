package com.ssspil.websocket.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "FILE_INFO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileInfo {

    @Id
    @Column(name = "FILE_ID")
    private String fileId;                      // 파일ID

    @Column(name = "FILE_PATH")
    private String filePath;                    // 파일경로

    @Column(name = "FILE_NAME")
    private String fileName;                    // 파일 이름

    @Column(name = "FILE_TYPE")
    private String fileType;                    // 파일 타입

    @Column(name = "FILE_EXT")
    private String fileExt;                     // 파일 확장자

    @Column(name = "FILE_SIZE")
    private Long fileSize;                      // 파일크기

    @Column(name = "STATUS")
    private String status;                      // 파일 상태

    @Column(name = "CREATE_AT")
    private LocalDateTime createAt;             // 생성 시각
}
