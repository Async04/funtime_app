package com.example.funtime_app.controller;

import com.example.funtime_app.dto.CategoryDTO;
import com.example.funtime_app.dto.request.CategorySaveDTO;
import com.example.funtime_app.interfaces.CategoryServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "Category API", description = "Category management API for handling categories and their tags.")
public class CategoryController {
    private final CategoryServiceInterface categoryServiceInterface;

    @Operation(
            summary = "Get tags by category ID",
            description = "Retrieve all tags associated with a specific category by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tags successfully retrieved"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            }
    )
    @GetMapping("/{id}")
    public HttpEntity<?> getAllTagsByCategoryId(@PathVariable String id) {
        return categoryServiceInterface.getAllTagsByCategory(id);
    }


    @Operation(
            summary = "Get all categories",
            description = "Retrieve a list of all categories.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categories successfully retrieved"),
                    @ApiResponse(responseCode = "404", description = "Categories not found!!!")
            }
    )
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        return categoryServiceInterface.getAllCategories();
    }

    @Operation(
            summary = "Save a new category",
            description = "Create a new category with the provided details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Category successfully created"),
                    @ApiResponse(responseCode = "404", description = "File not found"),
                    @ApiResponse(responseCode = "403", description = "Field must not be blank!!!")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategorySaveDTO categorySaveDTO) {
        return categoryServiceInterface.saveCategory(categorySaveDTO);
    }
}
