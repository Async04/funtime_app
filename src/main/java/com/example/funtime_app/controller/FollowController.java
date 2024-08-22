package com.example.funtime_app.controller;

import com.example.funtime_app.dto.FollowerDTO;
import com.example.funtime_app.interfaces.FollowServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/subscribe")
@RequiredArgsConstructor
@Tag(name = "Follow API", description = "API for managing user subscriptions, including following and unfollowing users.")
public class FollowController {
    private final FollowServiceImpl followService;

    @Operation(
            summary = "Follow a user",
            description = "Follow a user with the provided follower details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully followed"),
                    @ApiResponse(responseCode = "400", description = "Invalid follower data"),
                    @ApiResponse(responseCode = "500", description = "Internal server error"),
                    @ApiResponse(responseCode = "403", description = "Forbidden or bad attampt"),
                    @ApiResponse(responseCode = "400", description = "Already followed")

            }
    )
    @PostMapping("/follow")
    public HttpEntity<?> follow(@RequestBody FollowerDTO followerDto){
        return followService.follow(followerDto);
    }

    @Operation(
            summary = "Get follower count",
            description = "Retrieve the number of followers for a specific user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Follower count successfully retrieved"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error"),
                    @ApiResponse(responseCode = "403", description = "Forbidden or bad attampt")
            }
    )
    @GetMapping("/count")
    public HttpEntity<?> followerCount(@RequestParam UUID userId){
        return followService.followersCount(userId);
    }

    @Operation(
            summary = "Unfollow a user",
            description = "Unfollow a user with the provided user and follower IDs.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully unfollowed"),
                    @ApiResponse(responseCode = "400", description = "Invalid data provided"),
                    @ApiResponse(responseCode = "404", description = "User or follower not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error"),
                    @ApiResponse(responseCode = "403", description = "Forbidden or bad attampt")
            }
    )
    @PostMapping("/unfollow")
    public ResponseEntity<?> unfollow(@RequestParam UUID userId, @RequestParam UUID followerId){
        return followService.unfollow(userId, followerId);
    }
}
