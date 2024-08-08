package com.example.funtime_app.services;

import com.example.funtime_app.dto.FollowerDTO;
import com.example.funtime_app.entity.Follower;
import com.example.funtime_app.entity.User;
import com.example.funtime_app.interfaces.FollowServiceImpl;
import com.example.funtime_app.repository.FollowerRepository;
import com.example.funtime_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FollowService implements FollowServiceImpl {
    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    @Override
    public HttpEntity<?> follow(FollowerDTO followerDto) {
        Optional<User> followedByOpt = userRepository.findById(followerDto.getFollowedById());
        Optional<User> followerOpt = userRepository.findById(followerDto.getFollowerId());
        if(followerOpt.isPresent() && followerOpt.isPresent()){
            User followedBy = followedByOpt.get();
            User followerUser = followerOpt.get();
            Follower follower= Follower.builder()
                   .followedBy(followedBy)
                    .follower(followerUser)
                   .build();
            followerRepository.save(follower);
            return ResponseEntity.ok(follower);
        }
        return ResponseEntity.internalServerError().body("Not Followed!!!");
    }

    @Override
    public HttpEntity<?> followersCount(UUID userId) {
       Integer count= followerRepository.followersCount(userId);
       return ResponseEntity.ok(count);
    }
}
