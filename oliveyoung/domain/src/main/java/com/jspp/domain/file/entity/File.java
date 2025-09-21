package com.jspp.domain.file.entity;

import com.jspp.domain.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "web_service", name = "files")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class File extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_origin_name", nullable = false, length = 100)
    private String fileOriginName;

    @Column(name = "file_name", nullable = false, length = 100)
    private String fileName;

    @Column(name = "file_path", nullable = false, length = 200)
    private String filePath;

    @Column(name = "file_url", nullable = false, length = 100)
    private String fileUrl;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "file_type", nullable = false, length = 20)
    private String fileType;

    @Column(name = "file_ext", nullable = false, length = 10)
    private String fileExt;

}
