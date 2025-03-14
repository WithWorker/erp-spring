package com.erp.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadService {

    @Value("${spring.file-upload.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("파일이 비어 있습니다.");
        }

        String absolutePath = System.getProperty("user.dir") + File.separator + uploadDir;

        File dir = new File(absolutePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 파일 저장
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(absolutePath + File.separator + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return "upload/" + fileName;
    }
}
