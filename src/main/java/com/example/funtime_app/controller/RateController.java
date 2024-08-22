package com.example.funtime_app.controller;

import com.example.funtime_app.dto.request.PostRateDTO;
import com.example.funtime_app.interfaces.RateServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/rate")
@RequiredArgsConstructor
@Tag(name = "Rate API", description = "API for rating posts and retrieving rate values.")
public class RateController {

    private final RateServiceInterface rateServiceInterface;

    @Operation(
            summary = "Rate a post",
            description = "Submit a rating for a specific post by a user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post rated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid rating data")
            }
    )
    @PostMapping
    public ResponseEntity<?> ratePost(@RequestBody PostRateDTO rateDTO) {
        return rateServiceInterface.ratePost(rateDTO);
    }

    @Operation(
            summary = "Get rating value for a post",
            description = "Retrieve the rating value of a specific post by a user.",
            parameters = {
                    @Parameter(name = "postId", description = "ID of the post to retrieve the rating for", required = true),
                    @Parameter(name = "userId", description = "ID of the user who rated the post", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Rating value successfully retrieved"),
                    @ApiResponse(responseCode = "404", description = "Post or rating not found")
            }
    )
    @GetMapping("/post")
    public ResponseEntity<?> getRateValue(@RequestParam UUID postId, @RequestParam UUID userId) {
        return rateServiceInterface.getOnePostRate(postId, userId);
    }
}
