package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.FollowerDto;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public interface FollowServiceImpl {
    HttpEntity<?> follow(FollowerDto followerDto);
    HttpEntity<?> followersCount(UUID userId);
}
