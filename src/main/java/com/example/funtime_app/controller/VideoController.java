package com.example.funtime_app.controller;

import com.example.funtime_app.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/video")
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestVideo(){
        ResponseEntity<?> latestVideo = videoService.getLatestVideo();
        return latestVideo;

    }

}
