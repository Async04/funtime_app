package com.example.funtime_app.interfaces;

import com.example.funtime_app.dto.CategoryDTO;
import com.example.funtime_app.dto.request.CategorySaveDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryServiceInterface {
    ResponseEntity<List<CategoryDTO>> getAllCategories();


    HttpEntity<?> getAllTagsByCategory(String id);

    ResponseEntity<?> saveCategory(CategorySaveDTO categorySaveDTO);
}
