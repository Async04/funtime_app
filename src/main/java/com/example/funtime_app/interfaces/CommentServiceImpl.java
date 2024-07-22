package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.CommentDto;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;


import java.util.UUID;


public interface CommentServiceImpl {
    HttpEntity<?> getComments(UUID postId);
    HttpEntity<?> saveComment(CommentDto commentDto, UUID parentCommentId);
    HttpEntity<?> getChildComments(UUID parentCommentId);
}
