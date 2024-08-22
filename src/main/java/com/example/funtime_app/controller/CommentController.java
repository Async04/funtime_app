package com.example.funtime_app.controller;

import com.example.funtime_app.dto.request.CommentDTO;
import com.example.funtime_app.interfaces.CommentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@Tag(name = "Comment API", description = "API for managing comments, including retrieving and adding comments.")
public class CommentController {
    private final CommentServiceImpl commentService;

    @Operation(
            summary = "Get comments for a post",
            description = "Retrieve all comments associated with a specific post using the post ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comments successfully retrieved"),
                    @ApiResponse(responseCode = "404", description = "Comment not found")
            }
    )
    @GetMapping
    public HttpEntity<?> getPostComments(@RequestParam UUID postId){
        return commentService.getComments(postId);
    }

    @Operation(
            summary = "Get child comments",
            description = "Retrieve all child comments of a specific parent comment using the parent comment ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Child comments successfully retrieved"),
                    @ApiResponse(responseCode = "404", description = "Comment not found")
            }
    )
    @GetMapping("/child")
    public HttpEntity<?> getChildComments(@RequestParam UUID parentCommentId){
        return commentService.getChildComments(parentCommentId);
    }

    @Operation(
            summary = "Add a new comment",
            description = "Add a new comment to a post or as a child comment to another comment.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Comment successfully added"),
                    @ApiResponse(responseCode = "400", description = "Invalid comment data"),
                    @ApiResponse(responseCode = "404", description = "Not found!!!"),
                    @ApiResponse(responseCode = "500", description = "Not saved!!!")
            }
    )
    @PostMapping("/add")
    public HttpEntity<?> addComment(@RequestBody CommentDTO commentDto){
        return commentService.saveComment(commentDto);
    }
}
