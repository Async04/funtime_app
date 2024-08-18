package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.CommentDTO;
import org.springframework.http.HttpEntity;

import java.util.UUID;


public interface CommentServiceImpl {
    HttpEntity<?> getComments(UUID postId);
    HttpEntity<?> saveComment(CommentDTO commentDto);
    HttpEntity<?> getChildComments(UUID parentCommentId);
}
