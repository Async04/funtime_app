package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.CategoryTagDTO;
import com.example.funtime_app.dto.CreateCategoryDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public interface CategoryServiceInterface {


    HttpEntity<?> getAllTagsByCategory(String id);

    ResponseEntity<?> addNewCategory(CreateCategoryDTO createCategoryDTO);

    ResponseEntity<?> editCategory(String id, CreateCategoryDTO createCategoryDTO);

    ResponseEntity<?> deleteCategory(String id);

    ResponseEntity<?> addNewTag(CategoryTagDTO categoryTagDTO);
}
