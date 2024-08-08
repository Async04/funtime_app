package com.example.funtime_app.services;

import com.example.funtime_app.interfaces.VideoServiceInterface;
import com.example.funtime_app.projection.LatestVideoProjection;
import com.example.funtime_app.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService implements VideoServiceInterface {

    private final VideoRepository videoRepository;
    @Override
    public ResponseEntity<?> getLatestVideo() {

        List<LatestVideoProjection> videoDtos = videoRepository.getLatestVideo();
        return ResponseEntity.ok(videoDtos);

    }
}
