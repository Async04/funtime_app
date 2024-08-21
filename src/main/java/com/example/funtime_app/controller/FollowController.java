package com.example.funtime_app.controller;

import com.example.funtime_app.dto.FollowerDTO;
import com.example.funtime_app.interfaces.FollowServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscribe")
@RequiredArgsConstructor
public class FollowController {
    private final FollowServiceImpl followService;

    @PostMapping("/follow")
    public HttpEntity<?> follow(@RequestBody FollowerDTO followerDto){
        return followService.follow(followerDto);
    }


    @GetMapping("count")
    public HttpEntity<?> followerCount(UUID userId){
      return   followService.followersCount(userId);
    }

    @PostMapping("/unfollow")
    public ResponseEntity<?> unfollow(@RequestParam UUID userId, @RequestParam UUID followerId){
       return followService.unfollow(userId, followerId);
    }
}
