package com.example.funtime_app.controller;

import com.example.funtime_app.dto.request.VideoSaveDTO;
import com.example.funtime_app.services.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
@Tag(name = "Video API", description = "API for managing videos.")
public class VideoController {

    private final VideoService videoService;

    @Operation(
            summary = "Get latest video",
            description = "Retrieve the latest video available.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Latest video successfully retrieved"),
                    @ApiResponse(responseCode = "404", description = "No videos found")
            }
    )
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestVideo() {
        return videoService.getLatestVideo();
    }

    @Operation(
            summary = "Add view to video",
            description = "Increment the view count for a specific video.",
            parameters = {
                    @Parameter(name = "videoId", description = "ID of the video to increment view count for", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "View count successfully incremented"),
                    @ApiResponse(responseCode = "404", description = "Video not found")
            }
    )
    @PostMapping("/view")
    public ResponseEntity<?> addView(@RequestParam UUID videoId) {
        return videoService.addView(videoId);
    }

    @Operation(
            summary = "Save a new video",
            description = "Save a new video record.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Video successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid video data")
            }
    )
    @PostMapping("")
    public ResponseEntity<?> saveVideo(@RequestBody VideoSaveDTO videoSaveDTO) {
        return videoService.saveVideo(videoSaveDTO);
    }
}
