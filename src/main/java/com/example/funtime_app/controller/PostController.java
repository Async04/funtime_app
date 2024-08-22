package com.example.funtime_app.controller;

import com.example.funtime_app.dto.PostDTO;
import com.example.funtime_app.entity.Post;
import com.example.funtime_app.interfaces.PostServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Post API", description = "API for managing posts including CRUD operations, search, and filtering.")
public class PostController {

    private final PostServiceInterface postServiceInterface;

    @Operation(
            summary = "Save a new post",
            description = "Save a new post with the provided post data.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post successfully saved"),
                    @ApiResponse(responseCode = "400", description = "Invalid post data")
            }
    )
    @PostMapping("/save")
    public HttpEntity<?> saveNewPost(@RequestBody PostDTO postDto) {
        return postServiceInterface.savePost(postDto);
    }

    @Operation(
            summary = "Get posts with pagination",
            description = "Retrieve a paginated list of posts.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Posts successfully retrieved"),
                    @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
            }
    )
    @GetMapping
    public ResponseEntity<?> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return postServiceInterface.getPosts(page, size);
    }

    @Operation(
            summary = "Get posts by tags ID",
            description = "Retrieve posts associated with a specific tag ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Posts successfully retrieved"),
                    @ApiResponse(responseCode = "404", description = "Tag not found")
            }
    )
    @GetMapping("/{tagsId}")
    public ResponseEntity<?> getByTagsId(@PathVariable UUID tagsId) {
        return postServiceInterface.getAllTagsPost(tagsId);
    }

    @Operation(
            summary = "Get popular posts",
            description = "Retrieve a paginated list of popular posts.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Popular posts successfully retrieved")
            }
    )
    @GetMapping("/popular")
    public ResponseEntity<?> getPopularPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return postServiceInterface.getPopularPosts(page, size);
    }

    @Operation(
            summary = "Get new posts",
            description = "Retrieve a paginated list of new posts.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "New posts successfully retrieved")
            }
    )
    @GetMapping("/new")
    public ResponseEntity<?> getNewPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return postServiceInterface.getNewPosts(page, size);
    }

    @Operation(
            summary = "Get trendy posts",
            description = "Retrieve a paginated list of trendy posts.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trendy posts successfully retrieved")
            }
    )
    @GetMapping("/trendy")
    public ResponseEntity<?> getTrendyPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return postServiceInterface.getTrendyPosts(page, size);
    }

    @Operation(
            summary = "Get top posts",
            description = "Retrieve a paginated list of top posts.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Top posts successfully retrieved")
            }
    )
    @GetMapping("/top")
    public ResponseEntity<?> getTopPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return postServiceInterface.getTopPosts(page, size);
    }

    @Operation(
            summary = "Get posts by category ID",
            description = "Retrieve a paginated list of posts by category ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Posts successfully retrieved by category"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            }
    )
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getPostByCategoryId(
            @PathVariable UUID categoryId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return postServiceInterface.getByCategoryId(categoryId, page, size);
    }

    @Operation(
            summary = "Search posts",
            description = "Search for posts based on a search term.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Posts successfully retrieved based on search term"),
                    @ApiResponse(responseCode = "400", description = "Invalid search term")
            }
    )
    @GetMapping("/search")
    public HttpEntity<?> search(@RequestParam String search) {
        return postServiceInterface.getSearchedPosts(search);
    }

    @Operation(
            summary = "Get posts by user ID",
            description = "Retrieve posts created by a specific user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Posts successfully retrieved by user"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserPosts(@PathVariable UUID userId) {
        return postServiceInterface.getUserAllPosts(userId);
    }

    @Operation(
            summary = "Get a single post",
            description = "Retrieve a single post by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Post successfully retrieved"),
                    @ApiResponse(responseCode = "404", description = "Post not found")
            }
    )
    @GetMapping("/postId")
    public ResponseEntity<?> getOnePost(@RequestParam UUID postId) {
        return postServiceInterface.getOnePost(postId);
    }

    @Operation(
            summary = "Add a view to a post",
            description = "Increment the view count of a post.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "View count successfully updated"),
                    @ApiResponse(responseCode = "404", description = "Post not found")
            }
    )
    @PostMapping("/view")
    public ResponseEntity<?> addView(@RequestParam UUID postId) {
        return postServiceInterface.addView(postId);
    }
}
