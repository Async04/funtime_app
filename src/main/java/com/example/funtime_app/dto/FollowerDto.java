package com.example.funtime_app.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.example.funtime_app.entity.Follower}
 */
@Value
public class FollowerDto implements Serializable {
    UUID followerId;
    UUID followedById;
}