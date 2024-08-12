package com.example.funtime_app.controller;

import com.example.funtime_app.dto.CategoryDTO;
import com.example.funtime_app.entity.Category;
import com.example.funtime_app.interfaces.CategoryServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceInterface categoryServiceInterface;
    @GetMapping("/{id}")
    public HttpEntity<?> getAllTagsByCategoryId(@PathVariable String id){
        return categoryServiceInterface.getAllTagsByCategory(id);
    }





}
