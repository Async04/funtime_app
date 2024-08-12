package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.PostDTO;
import com.example.funtime_app.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface PostServiceInterface {

    HttpEntity<?> savePost(PostDTO postDto);

    Page<Post> getPosts(int page, int size);

    ResponseEntity<?> getPopularPosts(int page, int size);

    ResponseEntity<?> getNewPosts(int page, int size);

    ResponseEntity<?> getTrendyPosts(int page, int size);
    ResponseEntity<?> getTopPosts(int page, int size);

    ResponseEntity<?> getByCategoryId(UUID categoryId, Integer size, Integer page);

    HttpEntity<?> getSearchedPosts(String search);

    ResponseEntity<?> getUserAllPosts(UUID userId);
}
