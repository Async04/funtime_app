package com.example.funtime_app.controller;

import com.example.funtime_app.dto.request.TagSaveDTO;
import com.example.funtime_app.services.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
@Tag(name = "Tag API", description = "API for managing tags.")
public class TagController {

    private final TagService tagService;

    @Operation(
            summary = "Get tags by category ID",
            description = "Retrieve tags associated with a specific category by category ID.",
            parameters = {
                    @Parameter(name = "categoryId", description = "ID of the category to retrieve tags for", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tags successfully retrieved"),
                    @ApiResponse(responseCode = "404", description = "Category or tags not found")
            }
    )
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getTagsByCategoryId(@PathVariable UUID categoryId) {
        return tagService.getTagsByCategoryId(categoryId);
    }

    @Operation(
            summary = "Save a new tag",
            description = "Add a new tag to the system.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tag successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid tag data")
            }
    )
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveTag(@RequestBody TagSaveDTO tagSaveDTO) {
        return tagService.addTag(tagSaveDTO);
    }
}
