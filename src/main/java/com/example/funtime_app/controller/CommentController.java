package com.example.funtime_app.controller;

import com.example.funtime_app.dto.CommentDTO;
import com.example.funtime_app.interfaces.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentServiceImpl commentService;

    @GetMapping
    public HttpEntity<?> getPostComments(UUID postId){
        return commentService.getComments(postId);
    }

    @GetMapping("/child")
    public HttpEntity<?> getChildComments(UUID parentCommentId){
        return commentService.getChildComments(parentCommentId);
    }

    @PostMapping("/add")
    public HttpEntity<?> addComment(CommentDTO commentDto){
       return commentService.saveComment(commentDto);
    }

   

}
