package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.PostDTO;
import com.example.funtime_app.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PostServiceInterface {

    ResponseEntity<?> savePost(PostDTO postDto);

    ResponseEntity<?> getPosts(int page, int size);

    ResponseEntity<?> getPopularPosts(int page, int size);

    ResponseEntity<?> getNewPosts(int page, int size);

    ResponseEntity<?> getTrendyPosts(int page, int size);
    ResponseEntity<?> getTopPosts(int page, int size);

    ResponseEntity<?> getByCategoryId(UUID categoryId, Integer page, Integer size);

    HttpEntity<?> getSearchedPosts(String search);

    ResponseEntity<?> getUserAllPosts(UUID userId);

    ResponseEntity<?> getAllTagsPost(UUID tagsId);

    ResponseEntity<?> getOnePost(UUID postId);
}
