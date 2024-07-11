package com.example.funtime_app.controller;

import com.example.funtime_app.interfaces.CategoryServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceInterface categoryServiceInterface;
    @GetMapping("/{id}")
    public HttpEntity<?> getAllTagsByCategoryId(@PathVariable String id){
        return categoryServiceInterface.getAllTagsByCategory(id);
    }
}
