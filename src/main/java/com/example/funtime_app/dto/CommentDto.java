package com.example.funtime_app.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.example.funtime_app.entity.Comment}
 */
@Value
public class CommentDto implements Serializable {
    String body;
    UUID postId;
    UUID userId;
    UUID parentCommentId;
    Integer rateMarkValue;
}