package com.example.funtime_app.controller;

import com.example.funtime_app.dto.PostDto;
import com.example.funtime_app.entity.Post;
import com.example.funtime_app.interfaces.PostServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceInterface postServiceInterface;

    @PostMapping("/save")
    public HttpEntity<?> saveNewPost(PostDto postDto){
        return postServiceInterface.savePost(postDto);
    }

    @GetMapping()
    public Page<Post> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return postServiceInterface.getPosts(page,size);
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getPopularPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return postServiceInterface.getPopularPosts(page, size);
    }

    @GetMapping("/new")
    public ResponseEntity<?> getNewPosts(){
        return postServiceInterface.getNewPosts();
    }

     @GetMapping("/trendy")
    public ResponseEntity<?> getTrendyPosts(){
        return postServiceInterface.getTrendyPosts();
    }


    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getPostByCategoryId(@PathVariable UUID categoryId){
        return postServiceInterface.getByCategoryId(categoryId);
    }





}
