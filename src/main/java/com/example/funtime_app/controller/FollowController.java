package com.example.funtime_app.controller;

import com.example.funtime_app.dto.FollowerDto;
import com.example.funtime_app.interfaces.FollowServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/api/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowServiceImpl followService;

    @PostMapping
    public HttpEntity<?> follow(@RequestBody  FollowerDto followerDto){
       return followService.follow(followerDto);
    }


    @GetMapping("count")
    public HttpEntity<?> followerCount(UUID userId){
      return   followService.followersCount(userId);
    }
}
