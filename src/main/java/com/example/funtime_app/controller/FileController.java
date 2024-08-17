package com.example.funtime_app.controller;

import com.example.funtime_app.interfaces.FileServiceImpl;
import com.example.funtime_app.services.FileService;
import jakarta.mail.internet.ContentType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpResponse;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    @GetMapping("/photo/{id}")
    public void getPhoto(@PathVariable UUID id, HttpServletResponse response) {
        fileService.showPhoto(response, id);
    }

    @GetMapping("/video/{id}")
    public void getVideo(@PathVariable UUID id, HttpServletResponse response) {
        fileService.showVideo(response, id);
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file) {
        return fileService.saveFile(file);
    }


}
