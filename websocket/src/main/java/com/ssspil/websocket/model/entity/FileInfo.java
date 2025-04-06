package com.ssspil.websocket.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "FILE_INFO")
public class FileInfo {

    @Id
    @Column(name = "FILE_ID")
    private String fileId;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Column(name = "FILE_EXT")
    private String fileExt;

    @Column(name = "FILE_SIZE")
    private Long fileSize;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATE_AT")
    private LocalDateTime createAt;
}
