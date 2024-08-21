package com.example.funtime_app.services;

import com.example.funtime_app.dto.FollowerDTO;
import com.example.funtime_app.entity.Follower;
import com.example.funtime_app.entity.User;
import com.example.funtime_app.interfaces.FollowServiceImpl;
import com.example.funtime_app.repository.FollowerRepository;
import com.example.funtime_app.repository.UserRepository;
import jakarta.transaction.Transactional;
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

        try
        {
            Optional<User> followedByOpt = userRepository.findById(followerDto.getFollowedById());
            Optional<User> followerOpt = userRepository.findById(followerDto.getFollowerId());
            if(followerOpt.isPresent() && followedByOpt.isPresent()){
                User followedBy = followedByOpt.get();
                User followerUser = followerOpt.get();
                Follower follower = Follower.builder()
                        .followedBy(followedBy)
                        .follower(followerUser)
                        .build();
                followerRepository.save(follower);
                return ResponseEntity.ok("Followed successfully!!!");
            }
            return ResponseEntity.internalServerError().body("Not Followed!!!");
        }
       catch (Exception e){
            return ResponseEntity.badRequest().body("Already followed or bad request!!!");
       }

    }

    @Override
    public HttpEntity<?> followersCount(UUID userId) {
       Integer count= followerRepository.followersCount(userId);
       return ResponseEntity.ok(count);
    }

    @Override
    @Transactional
    public ResponseEntity<?> unfollow(UUID userId, UUID followerId) {
        try {
            followerRepository.deleteByFollowerIdAndFollowedById(followerId, userId);
            return ResponseEntity.ok("Successfully unfollowed!!!");
        }
        catch (Exception e){
           return  ResponseEntity.badRequest().body("Error unfollowing");
        }

    }
}
