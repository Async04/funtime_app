package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.request.VideoSaveDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface VideoServiceInterface {

    ResponseEntity<?> getLatestVideo();

    ResponseEntity<?> addView(UUID videoId);

    public ResponseEntity<?> saveVideo(VideoSaveDTO videoSaveDTO);
}
