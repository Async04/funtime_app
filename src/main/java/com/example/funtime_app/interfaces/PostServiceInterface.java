package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.PostDto;
import com.example.funtime_app.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface PostServiceInterface {

    HttpEntity<?> savePost(PostDto postDto);

    Page<Post> getPosts(int page, int size);

    ResponseEntity<?> getPopularPosts();

    ResponseEntity<?> getNewPosts();

    ResponseEntity<?> getTrendyPosts();

    ResponseEntity<?> getByCategoryId(UUID categoryId);
}
