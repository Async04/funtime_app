package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.FollowerDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public interface FollowServiceImpl {
    HttpEntity<?> follow(FollowerDTO followerDto);
    HttpEntity<?> followersCount(UUID userId);

    ResponseEntity<?> unfollow(UUID userId, UUID followerId);
}
