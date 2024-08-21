package com.example.funtime_app.services;

import com.example.funtime_app.dto.request.CategoryRequestTagDTO;
import com.example.funtime_app.dto.request.VideoSaveDTO;
import com.example.funtime_app.entity.*;
import com.example.funtime_app.interfaces.VideoServiceInterface;
import com.example.funtime_app.projection.LatestVideoProjection;
import com.example.funtime_app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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

        List<LatestVideoProjection> videoDtos = videoRepository.getLatestVideo();
        if (videoDtos.isEmpty()){
            return ResponseEntity.badRequest().body("Not found videos!!!");
        }
        return ResponseEntity.ok(videoDtos);

    }

    @Override
    public ResponseEntity<?> addView(UUID videoId) {
        Optional<Video> byId = videoRepository.findById(videoId);
        if (byId.isPresent()){
            Video video = byId.get();
            video.setViews(video.getViews()+1);
            videoRepository.save(video);
            return ResponseEntity.ok("Viewed!!!");
        }

        return ResponseEntity.badRequest().body("Video not found!!!");
    }


    @Override
    public ResponseEntity<?> saveVideo(VideoSaveDTO videoSaveDTO) {

        Optional<Attachment> optionalAttachment
                = attachmentRepository.findById(videoSaveDTO.getAttachmentId());
        Optional<User> optionalUser
                = userRepository.findById(videoSaveDTO.getUserId());
        Optional<Category> optionalCategory
                = categoryRepository.findById(videoSaveDTO.getCategoryId());

        List<CategoryTag> categoryTags = new ArrayList<>();

        for (CategoryRequestTagDTO tag : videoSaveDTO.getTags()) {
            Optional<CategoryTag> byId = categoryTagRepository.findById(tag.getTagId());
            if (byId.isPresent()){
                CategoryTag categoryTag = byId.get();
                categoryTags.add(categoryTag);
            }
        }

        if (optionalAttachment.isEmpty()){
            return ResponseEntity.badRequest().body("File not found");
        }
        if (optionalUser.isEmpty()){
            return ResponseEntity.badRequest().body("User not found!!!");
        }
        if (optionalCategory.isEmpty()){
            return ResponseEntity.badRequest().body("Category not found!!!");
        }

        Attachment attachment = optionalAttachment.get();

        Video video = Video.builder()
                .tags(categoryTags)
                .user(optionalUser.get())
                .views(0)
                .title(videoSaveDTO.getTitle())
                .description(videoSaveDTO.getDescription())
                .category(optionalCategory.get())
                .attachment(attachment)
                .build();

        videoRepository.save(video);

        return ResponseEntity.ok("Video saved!!!");
    }
}
