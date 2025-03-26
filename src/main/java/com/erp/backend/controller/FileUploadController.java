package com.erp.backend.controller;

import com.erp.backend.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping("/admin/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            return fileUploadService.uploadImage(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "파일 업로드 실패";
        }
    }
}
