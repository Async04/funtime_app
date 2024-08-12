package com.example.funtime_app.controller;

import com.example.funtime_app.dto.CategoryTagDTO;
import com.example.funtime_app.dto.CreateCategoryDTO;
import com.example.funtime_app.entity.CategoryTag;
import com.example.funtime_app.interfaces.CategoryServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final CategoryServiceInterface categoryServiceInterface;

    @PostMapping("/add/category")
    public ResponseEntity<?> addNewCategory(@RequestBody CreateCategoryDTO createCategoryDTO){
       return categoryServiceInterface.addNewCategory(createCategoryDTO);
    }

    @PutMapping("/edit/category/{id}")
    public ResponseEntity<?> editCategory(@PathVariable String id,@RequestBody CreateCategoryDTO createCategoryDTO){
        return categoryServiceInterface.editCategory(id,createCategoryDTO);
    }
    @DeleteMapping("/delete/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        return categoryServiceInterface.deleteCategory(id);
    }

    @PostMapping("/add/tag")
    public ResponseEntity<?> addTag(@RequestBody CategoryTagDTO categoryTagDTO){
        return categoryServiceInterface.addNewTag(categoryTagDTO);
    }
}
