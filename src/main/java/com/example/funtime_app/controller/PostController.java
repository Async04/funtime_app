package com.example.funtime_app.controller;

import com.example.funtime_app.dto.PostDTO;
import com.example.funtime_app.entity.Post;
import com.example.funtime_app.interfaces.PostServiceInterface;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceInterface postServiceInterface;

    @PostMapping("/save")
    public ResponseEntity<?> saveNewPost(
            @RequestBody PostDTO postDTO
    ) {
        System.out.println("LLLLLLLLLLLLLLLLL");
        System.out.println(postDTO);
        System.out.println("SSSSSSSSSS");
        return postServiceInterface.savePost(postDTO);
    }

    @GetMapping()
    public ResponseEntity<?> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return postServiceInterface.getPosts(page, size);
    }

    @GetMapping("/{tagsId}")
    public ResponseEntity<?> getByTagsId(@PathVariable UUID tagsId){
        return postServiceInterface.getAllTagsPost(tagsId);

    }




    @GetMapping("/popular")
    public ResponseEntity<?> getPopularPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return postServiceInterface.getPopularPosts(page, size);
    }

    @GetMapping("/new")
    public ResponseEntity<?> getNewPosts(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ) {
        return postServiceInterface.getNewPosts(page, size);
    }

    @GetMapping("/trendy")
    public ResponseEntity<?> getTrendyPosts(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ) {
        return postServiceInterface.getTrendyPosts(page, size);
    }

    @GetMapping("/top")
    public ResponseEntity<?> getTopPosts(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ) {
        return postServiceInterface.getTopPosts(page, size);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getPostByCategoryId(
            @PathVariable UUID categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ) {
        return postServiceInterface.getByCategoryId(categoryId, page, size);
    }


    @GetMapping("/search")
    public HttpEntity<?> search(String search) {
        return postServiceInterface.getSearchedPosts(search);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserPosts(@PathVariable UUID userId) {
        return postServiceInterface.getUserAllPosts(userId);
    }

    @GetMapping("/postId")
    public ResponseEntity<?> getOnePost(@RequestParam UUID postId){
        return postServiceInterface.getOnePost( postId);
    }

    @PostMapping("/view")
    public ResponseEntity<?> addView(@RequestParam UUID postId){
        return postServiceInterface.addView(postId);

    }

}
