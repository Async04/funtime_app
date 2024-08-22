package com.example.funtime_app.services;

import com.example.funtime_app.dto.request.CategoryRequestTagDTO;
import com.example.funtime_app.dto.request.VideoSaveDTO;
import com.example.funtime_app.entity.*;
import com.example.funtime_app.interfaces.VideoServiceInterface;
import com.example.funtime_app.projection.LatestVideoProjection;
import com.example.funtime_app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService implements VideoServiceInterface {

    private final VideoRepository videoRepository;
    private final AttachmentRepository attachmentRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryTagRepository categoryTagRepository;
    @Override
    public ResponseEntity<?> getLatestVideo() {
        try {
            List<LatestVideoProjection> videoDtos = videoRepository.getLatestVideo();
            if (videoDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No videos found");
            }
            return ResponseEntity.ok(videoDtos);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving the latest video");
        }
    }


    @Override
    public ResponseEntity<?> addView(UUID videoId) {
        if (videoId == null || videoId.toString().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid video ID");
        }
        Optional<Video> videoOptional = videoRepository.findById(videoId);
        if (videoOptional.isPresent()) {
            Video video = videoOptional.get();
            video.setViews(video.getViews() + 1);
            videoRepository.save(video);
            return ResponseEntity.ok("View count successfully incremented");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found");
        }
    }



    @Override
    public ResponseEntity<?> saveVideo(VideoSaveDTO videoSaveDTO) {

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(videoSaveDTO.getAttachmentId());
        Optional<User> optionalUser = userRepository.findById(videoSaveDTO.getUserId());
        Optional<Category> optionalCategory = categoryRepository.findById(videoSaveDTO.getCategoryId());


        List<CategoryTag> categoryTags = new ArrayList<>();
        List<UUID> missingTags = new ArrayList<>();


        for (CategoryRequestTagDTO tag : videoSaveDTO.getTags()) {
            Optional<CategoryTag> byId = categoryTagRepository.findById(tag.getTagId());
            if (byId.isPresent()) {
                categoryTags.add(byId.get());
            } else {

                missingTags.add(tag.getTagId());
            }
        }


        if (optionalAttachment.isEmpty()) {
            return ResponseEntity.badRequest().body("Attachment not found");
        }
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.badRequest().body("Category not found");
        }
        if (!missingTags.isEmpty()) {

            return ResponseEntity.badRequest().body("Tags not found: " + missingTags.toString());
        }


        Video video = Video.builder()
                .tags(categoryTags)
                .user(optionalUser.get())
                .views(0)
                .title(videoSaveDTO.getTitle())
                .description(videoSaveDTO.getDescription())
                .category(optionalCategory.get())
                .attachment(optionalAttachment.get())
                .build();

        videoRepository.save(video);

        return ResponseEntity.ok("Video saved successfully");
    }

}
