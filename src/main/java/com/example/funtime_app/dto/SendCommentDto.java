package com.example.funtime_app.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.example.funtime_app.entity.Comment}
 */
@Value
public class SendCommentDto implements Serializable {
    UUID id;
    String body;
    UserDTO commentedBy;
    LocalDateTime createdAt;
    Integer rateMarkValue;
}