package com.example.funtime_app.controller;

import com.example.funtime_app.interfaces.FileServiceImpl;
import com.example.funtime_app.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    @GetMapping("/photo/{id}")
    public void getPhoto(@PathVariable UUID id, HttpServletResponse response){
        fileService.showPhoto(response,id);
    }

    @GetMapping("/video/{id}")
    public void getVideo(@PathVariable UUID id, HttpServletResponse response){
        fileService.showVideo(response,id);
    }



}
