package com.erp.backend.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileVO {
    private String fileId;         // 파일 고유 ID (Primary Key 역할)
    private String fileName;    // 파일명 (예: "report.pdf")
    private String filePath;    // 파일 저장 경로 (예: "/uploads/2024/02/")
    private String fileType;    // 파일 유형 (예: "pdf", "jpg", "png" 등)
    private long fileSize;      // 파일 크기 (바이트 단위)
    private String messengerId;    // 해당 파일이 포함된 메시지 ID (messenger 테이블 참조)
    private String uploaderId;     // 파일 업로더(보낸 사람) ID (employee 테이블 참조)
    private LocalDateTime uploadTime; // 파일 업로드 시간

}
