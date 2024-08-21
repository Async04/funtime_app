package com.example.funtime_app.controller;

import com.example.funtime_app.dto.request.VideoSaveDTO;
import com.example.funtime_app.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/video")
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestVideo(){
        return videoService.getLatestVideo();

    }

    @PostMapping("/view")
    public ResponseEntity<?> addView(@RequestParam UUID videoId){
        return videoService.addView(videoId);
    }

    @PostMapping("")
    public ResponseEntity<?> saveVideo(@RequestBody VideoSaveDTO videoSaveDTO){
        return videoService.saveVideo(videoSaveDTO);
    }
}
